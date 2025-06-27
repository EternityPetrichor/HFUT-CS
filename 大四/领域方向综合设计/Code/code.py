import os
import numpy as np
from PIL import Image
import torch
import torch.nn as nn
from torch.utils.data import DataLoader, Dataset
from torchvision import transforms
from torchvision.utils import save_image

# --------- 1. 数据集预处理 ---------
class ContourDataset(Dataset):
    def __init__(self, folder_path, img_size, output_folder):
        """
               初始化数据集
               :param folder_path: 原始图像存储的文件夹路径
               :param img_size: 目标图像大小 (width, height)
               :param output_folder: 预处理后的图像保存路径
               """
        self.folder_path = folder_path
        self.img_size = img_size
        self.output_folder = output_folder
        os.makedirs(output_folder, exist_ok=True)
        self.image_paths = [
            os.path.join(folder_path, img) for img in os.listdir(folder_path) if img.endswith((".png", ".jpg"))
        ]# 筛选出文件夹中所有 PNG 或 JPG 格式的图像
        self.transform = transforms.Compose([
            transforms.Resize(img_size),# 调整图像大小
            transforms.ToTensor(),# 转为 PyTorch 张量
            transforms.Normalize([0.5], [0.5])  # Normalize to [-1, 1]
        ])# 将图像数据归一化到 [-1, 1]

    def __len__(self):
        return len(self.image_paths)

    def __getitem__(self, idx):
        """
                加载并预处理单张图像
                :param idx: 图像索引
                :return: 预处理后的图像张量
                """
        img_path = self.image_paths[idx]
        img = Image.open(img_path).convert("L")  # 转为灰度图像
        img_resized = img.resize(self.img_size)  # Resize before tensor conversion
        img_resized.save(os.path.join(self.output_folder, f"resized_{idx}.png"))  # Save resized image
        img = self.transform(img_resized)
        return img

# --------- 2. 定义生成器 ---------
class Generator(nn.Module):
    """
        定义生成器，用于从随机噪声生成图像
        """
    def __init__(self, latent_dim, img_shape):
        """
                初始化生成器
                :param latent_dim: 潜在向量的维度
                :param img_shape: 输出图像的形状 (channels, width, height)
                """
        super(Generator, self).__init__()
        self.init_size = img_shape[1] // 4  # 上采样前的初始图像尺寸
        self.l1 = nn.Sequential(nn.Linear(latent_dim, 128 * self.init_size ** 2))
        # 全连接层，用于扩展潜在向量

        # 转置卷积和上采样模块
        self.conv_blocks = nn.Sequential(
            nn.BatchNorm2d(128),# 批归一化，稳定训练
            nn.Upsample(scale_factor=2),# 上采样
            nn.Conv2d(128, 128, 3, stride=1, padding=1),# 卷积层
            nn.BatchNorm2d(128),
            nn.LeakyReLU(0.2, inplace=True),# 激活函数
            nn.Upsample(scale_factor=2),# 再次上采样
            nn.Conv2d(128, 64, 3, stride=1, padding=1),
            nn.BatchNorm2d(64),
            nn.LeakyReLU(0.2, inplace=True),
            nn.Conv2d(64, img_shape[0], 3, stride=1, padding=1),# 输出卷积层
            nn.Tanh(),# 将输出归一化到 [-1, 1]
        )

    def forward(self, z):
        """
                前向传播
                :param z: 输入的潜在向量
                :return: 生成的图像
                """
        out = self.l1(z)# 扩展潜在向量
        out = out.view(out.size(0), 128, self.init_size, self.init_size)# 调整形状为特征图
        img = self.conv_blocks(out)# 通过卷积块生成图像
        return img

# --------- 3. 定义判别器 ---------
class Discriminator(nn.Module):
    """
        定义判别器，用于区分真实图像和生成图像
        """
    def __init__(self, img_shape):
        super(Discriminator, self).__init__()
        self.model = nn.Sequential(
            nn.Conv2d(img_shape[0], 64, 3, stride=2, padding=1),# 卷积层，提取特征
            nn.LeakyReLU(0.2, inplace=True),
            nn.Dropout(0.25),# Dropout 防止过拟合
            nn.Conv2d(64, 128, 3, stride=2, padding=1),
            nn.BatchNorm2d(128),
            nn.LeakyReLU(0.2, inplace=True),
            nn.Dropout(0.25),
            nn.Flatten(),# 展平张量以输入全连接层
            nn.Linear(128 * (img_shape[1] // 4) * (img_shape[2] // 4), 1),#输出一个标量
            nn.Sigmoid(),# 输出概率值
        )

    def forward(self, img):
        """
               前向传播
               :param img: 输入的图像
               :return: 图像的真实性评分
               """
        validity = self.model(img)
        return validity

# --------- 4. 定义训练过程 ---------
def train_gan(generator, discriminator, data_loader, latent_dim, img_shape, epochs=200, batch_size=32, lr=0.0002):
    """
       训练生成对抗网络
       :param generator: 生成器
       :param discriminator: 判别器
       :param data_loader: 数据加载器
       :param latent_dim: 潜在向量维度
       :param img_shape: 图像形状
       :param epochs: 训练轮次
       :param batch_size: 批量大小
       :param lr: 学习率
       """
    device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    generator.to(device)
    discriminator.to(device)

    #优化器
    optimizer_G = torch.optim.Adam(generator.parameters(), lr=lr, betas=(0.5, 0.999))
    optimizer_D = torch.optim.Adam(discriminator.parameters(), lr=lr, betas=(0.5, 0.999))
    adversarial_loss = nn.BCELoss()# 二分类交叉熵损失

    # 打开文件用于写入损失值
    with open("losses.txt", "w") as f:
        f.write("Epoch D_loss G_loss\n")  # 写入表头

        for epoch in range(epochs):
            for i, imgs in enumerate(data_loader):
                # Configure input
                real_imgs = imgs.to(device)

                # Train Discriminator
                optimizer_D.zero_grad()
                z = torch.randn(imgs.size(0), latent_dim).to(device)# 生成随机噪声
                fake_imgs = generator(z)# 生成假图像
                # 计算真实图像和假图像的损失
                real_loss = adversarial_loss(discriminator(real_imgs), torch.ones(imgs.size(0), 1).to(device))
                fake_loss = adversarial_loss(discriminator(fake_imgs.detach()), torch.zeros(imgs.size(0), 1).to(device))
                d_loss = (real_loss + fake_loss) / 2# 判别器总损失
                d_loss.backward()
                optimizer_D.step()

                # Train Generator
                optimizer_G.zero_grad()
                g_loss = adversarial_loss(discriminator(fake_imgs), torch.ones(imgs.size(0), 1).to(device))
                g_loss.backward()
                optimizer_G.step()

            # 打印损失值
            print(f"[Epoch {epoch}/{epochs}] [D loss: {d_loss.item()}] [G loss: {g_loss.item()}]")

            # 写入损失值到文件
            f.write(f"{epoch} {d_loss.item():.6f} {g_loss.item():.6f}\n")

            # Save generated samples every 10 epochs
            if epoch % 10 == 0:
                save_image(fake_imgs.data[:25], f"images/{epoch}.png", nrow=5, normalize=True)

        # Generate and save the 8th continent image
        z = torch.randn(1, latent_dim).to(device)
        generated_img = generator(z)
        save_image(generated_img.data, "images/8th_continent.png", normalize=True)
        print("Generated the 8th continent image.")

# --------- 5. 执行代码 ---------
if __name__ == "__main__":
    # 数据路径和图像大小
    data_path = "./data"
    # data_path = "./map/map1"
    output_folder = "./resized_images"
    # output_folder = "./map/resized_images"
    img_size = (64, 64)  # 将所有图像裁剪为相同大小
    latent_dim = 100
    img_shape = (1, *img_size)  # 灰度图像有1个通道

    # 加载数据
    dataset = ContourDataset(data_path, img_size, output_folder)
    data_loader = DataLoader(dataset, batch_size=32, shuffle=True)

    # 初始化模型
    generator = Generator(latent_dim, img_shape)
    discriminator = Discriminator(img_shape)

    # 训练GAN
    train_gan(generator, discriminator, data_loader, latent_dim, img_shape,epochs = 10000)