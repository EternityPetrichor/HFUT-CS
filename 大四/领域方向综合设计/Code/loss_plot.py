import matplotlib.pyplot as plt

# 读取loss.txt文件
data = []
with open('loss.txt', 'r') as file:
    # 跳过标题行
    file.readline()
    for line in file:
        parts = line.strip().split()
        if len(parts) == 3:
            epoch, d_loss, g_loss = map(float, parts)
            data.append((epoch, d_loss, g_loss))

# 提取数据
epochs = [item[0] for item in data]
d_losses = [item[1] for item in data]
g_losses = [item[2] for item in data]

# 绘制图像
plt.figure(figsize=(10, 6))
plt.plot(epochs, d_losses, label='D_loss', color='blue', linewidth=1.5)
plt.plot(epochs, g_losses, label='G_loss', color='orange', linewidth=1.5)

# 添加标题和标签
plt.title('Generator and Discriminator Losses', fontsize=14)
plt.xlabel('Epoch', fontsize=12)
plt.ylabel('Loss', fontsize=12)

# 添加图例
plt.legend(fontsize=12)

# 显示网格
plt.grid(alpha=0.5)

# 显示图像
plt.tight_layout()
plt.show()
