import os
from PIL import Image, ImageEnhance, ImageFilter
import numpy as np
import random

# 定义输入和输出文件夹路径
input_folder = "map"
output_folder = os.path.join(input_folder, "map1")

# 如果输出文件夹不存在，创建它
os.makedirs(output_folder, exist_ok=True)

# 获取输入文件夹中的所有图片文件，并按文件名的数字排序
image_files = sorted([f for f in os.listdir(input_folder) if f.endswith(('.png', '.jpg', '.jpeg'))], key=lambda x: int(x.split('.')[0]))

# 目标生成图片的数量
target_images_count = 560
generated_images_count = 7  # 从7开始

# 裁剪图片
def crop_image(image, output_folder):
    global generated_images_count
    # 定义裁剪比例
    crop_ratios = [0.8, 0.6, 0.4]  # 保留 80%、60%、40% 的区域
    width, height = image.size

    for ratio in crop_ratios:
        if generated_images_count >= target_images_count:
            return
        # 计算裁剪区域，以图像中心为基准
        crop_width = int(width * ratio)
        crop_height = int(height * ratio)
        left = (width - crop_width) // 2
        upper = (height - crop_height) // 2
        right = left + crop_width
        lower = upper + crop_height

        # 执行裁剪
        cropped = image.crop((left, upper, right, lower))
        cropped.save(os.path.join(output_folder, f"{generated_images_count}.jpg"))
        generated_images_count += 1
# 添加噪声处理
def add_noise(image, noise_type="gaussian"):
    """
    添加噪声到图片
    :param image: PIL Image 对象
    :param noise_type: 噪声类型，"gaussian" 或 "salt_pepper"
    :return: 含噪声的 PIL Image 对象
    """
    np_image = np.array(image)

    if noise_type == "gaussian":
        # 高斯噪声
        mean = 0
        stddev = 25
        gauss = np.random.normal(mean, stddev, np_image.shape).astype(np.int16)
        noisy_image = np.clip(np_image + gauss, 0, 255).astype(np.uint8)
    elif noise_type == "salt_pepper":
        # 椒盐噪声
        prob = 0.05
        noisy_image = np_image.copy()
        salt = np.random.choice([0, 255], noisy_image.shape, p=[1 - prob, prob])
        pepper = np.random.choice([0, 255], noisy_image.shape, p=[1 - prob, prob])
        noisy_image[salt == 255] = 255
        noisy_image[pepper == 255] = 0
    else:
        raise ValueError("Unsupported noise type")

    return Image.fromarray(noisy_image)
# 定义数据增强函数
def augment_image(image_path, output_folder):
    global generated_images_count
    try:
        # 打开图像
        image = Image.open(image_path)

        # 生成旋转的图片
        for angle in [0, 90, 180, 270, 45, 135, 225, 315]:  # 添加更多角度
            if generated_images_count >= target_images_count:
                return
            rotated = image.rotate(angle)
            rotated.save(os.path.join(output_folder, f"{generated_images_count}.jpg"))
            generated_images_count += 1

        # 缩放图片
        for scale in [0.5, 0.7, 0.9, 1, 1.1, 1.3, 1.5, 1.7, 1.9, 2]:  # 扩展缩放比例
            if generated_images_count >= target_images_count:
                return
            new_size = (int(image.width * scale), int(image.height * scale))
            resized = image.resize(new_size)
            resized.save(os.path.join(output_folder, f"{generated_images_count}.jpg"))
            generated_images_count += 1

        # 裁剪图片
        # for _ in range(15):  # 增加裁剪次数
        #     if generated_images_count >= target_images_count:
        #         return
        #     left = random.randint(0, image.width // 2)
        #     upper = random.randint(0, image.height // 2)
        #     right = random.randint(image.width // 2, image.width)
        #     lower = random.randint(image.height // 2, image.height)
        #     cropped = image.crop((left, upper, right, lower))
        #     cropped.save(os.path.join(output_folder, f"{generated_images_count}.jpg"))
        #     generated_images_count += 1
        crop_image(image, output_folder)

        # 模糊处理
        for blur_radius in [1, 2, 3, 5]:  # 不同的模糊半径
            if generated_images_count >= target_images_count:
                return
            blurred = image.filter(ImageFilter.GaussianBlur(blur_radius))
            blurred.save(os.path.join(output_folder, f"{generated_images_count}.jpg"))
            generated_images_count += 1

        # 颜色亮度调整
        for brightness_factor in [0.5, 0.8, 1.2, 1.5]:  # 不同的亮度调整因子
            if generated_images_count >= target_images_count:
                return
            enhancer = ImageEnhance.Brightness(image)
            brightened = enhancer.enhance(brightness_factor)
            brightened.save(os.path.join(output_folder, f"{generated_images_count}.jpg"))
            generated_images_count += 1

        # 添加噪声
        for noise_type in ["gaussian", "salt_pepper"]:
            if generated_images_count >= target_images_count:
                return
            noisy = add_noise(image, noise_type=noise_type)
            noisy.save(os.path.join(output_folder, f"{generated_images_count}.jpg"))
            generated_images_count += 1

    except Exception as e:
        print(f"Error processing {image_path}: {e}")

# 对每张图片进行数据增强
for file_name in image_files:
    file_path = os.path.join(input_folder, file_name)
    augment_image(file_path, output_folder)
    if generated_images_count >= target_images_count:
        break

print(f"数据增强完成，处理后的图片已保存到 {output_folder} 文件夹中，共生成 {generated_images_count - 7} 张图片。")