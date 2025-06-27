import os

def rename_files_with_increment(folder_path, increment):
    # 获取文件夹中的所有文件名，确保按数字排序
    files = [f for f in os.listdir(folder_path) if f.lower().endswith(".jpg")]
    files.sort(key=lambda x: int(x.split('.')[0]))  # 按数字排序

    for file in files:
        # 获取当前文件名的数字部分
        current_number = int(file.split('.')[0])

        # 计算新的文件名
        new_number = current_number + increment
        new_name = f"{new_number}.jpg"

        # 获取旧路径和新路径
        old_path = os.path.join(folder_path, file)
        new_path = os.path.join(folder_path, new_name)

        # 重命名
        os.rename(old_path, new_path)
        print(f"Renamed: {file} -> {new_name}")

# 使用示例
folder = "map1"  # 替换为你的文件夹路径
increment_value = 448  # 每次增加的数值
rename_files_with_increment(folder, increment_value)
