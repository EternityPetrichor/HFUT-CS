import numpy as np

class HopfieldTSP:
    def __init__(self, distance_matrix, learning_rate=0.1, threshold=0, epochs=1000):
        self.distance_matrix = distance_matrix
        self.num_cities = len(distance_matrix)
        self.weights = self._calculate_weights()
        self.learning_rate = learning_rate
        self.threshold = threshold
        self.epochs = epochs

    def _calculate_weights(self):
        weights = np.zeros((self.num_cities, self.num_cities))
        for i in range(self.num_cities):
            for j in range(self.num_cities):
                if i != j:
                    weights[i][j] = -self.distance_matrix[i][j]
        return weights

    def energy(self, state):
        total_energy = 0
        for i in range(self.num_cities):
            for j in range(self.num_cities):
                total_energy += self.weights[i][j] * state[i] * state[j]
        return total_energy / 2

    def update(self, state):
        for _ in range(self.epochs):
            i = np.random.randint(0, self.num_cities)
            state[i] = 1 if np.sum(self.weights[i] * state) > self.threshold else 0
        return state

    def optimize(self):
        initial_state = np.random.randint(0, 2, size=self.num_cities)
        state = initial_state.copy()
        optimized_state = self.update(state)
        return optimized_state

def reconstruct_path(optimized_state):
    remaining_cities = list(range(len(optimized_state)))  # 所有城市的索引列表
    path = []  # 存储路径
    current_city = np.random.choice(remaining_cities)  # 随机选择一个起始城市
    path.append(current_city)
    remaining_cities.remove(current_city)

    while remaining_cities:
        next_city = np.argmax(optimized_state[current_city])  # 选择下一个城市
        if next_city in remaining_cities:
            optimized_state[current_city] = 0  # 防止重复访问同一个城市
            path.append(next_city)
            remaining_cities.remove(next_city)
            current_city = next_city
        else:
            # 如果城市已经访问过，寻找一个未访问的城市
            for city in range(len(optimized_state)):
                if city in remaining_cities:
                    current_city = city
                    path.append(current_city)
                    remaining_cities.remove(current_city)
                    break

    # 确保回到起始城市
    path.append(path[0])

    return path

def read_tsp_file(filename):
    metadata = {}
    with open(filename, 'r') as file:
        lines = file.readlines()
        for line in lines:
            if line.startswith("DIMENSION"):
                metadata["num_cities"] = int(line.split(":")[1])
            # 可以根据需要解析其他元数据
            
    return metadata

def read_distance_matrix(filename, num_cities):
    with open(filename, 'r') as file:
        lines = file.readlines()
    
    distance_matrix = []
    for line in lines:
        row = [int(dist) for dist in line.strip().split()]
        distance_matrix.append(row)
    
    return distance_matrix


num_cities=42


# 创建自己的距离矩阵 
num_cities = 6
custom_distance_matrix = np.array([
    [0, 10, 20, 15, 30, 5],   # 城市0到其他城市的距离
    [10, 0, 25, 20, 35, 13],   # 城市1到其他城市的距离
    [20, 25, 0, 12, 28, 23],   # 城市2到其他城市的距离
    [15, 20, 12, 0, 22, 20],   # 城市3到其他城市的距离
    [30, 35, 28, 22, 0, 18],    # 城市4到其他城市的距离
    [5, 13, 23, 20, 18, 0]
])

# 使用自定义距离矩阵创建HopfieldTSP实例
hopfield_tsp = HopfieldTSP(custom_distance_matrix, learning_rate=0.5, threshold=0.3, epochs=100)
optimized_state = hopfield_tsp.optimize()

# 重建最优路径
full_path = reconstruct_path(optimized_state)
shortest_distance = sum(custom_distance_matrix[full_path[i]][full_path[i + 1]] for i in range(len(full_path) - 1))
print("优化状态",optimized_state)
print("完整路径:", full_path)
print("最短距离:", shortest_distance)


'''
distance_matrix = read_distance_matrix("dantzig42_d.txt",42) #不使用tsp文件
#tsp_metadata = read_tsp_file("dantzig42.tsp")  使用tsp文件
#distance_matrix = read_distance_matrix("dantzig42_d.txt", tsp_metadata["num_cities"]) 使用tsp文件
hopfield_tsp = HopfieldTSP(distance_matrix, learning_rate=0.5, threshold=0.3, epochs=1000)
optimized_state = hopfield_tsp.optimize()

full_path = reconstruct_path(optimized_state)
shortest_distance = sum(distance_matrix[full_path[i]][full_path[i + 1]] for i in range(len(full_path) - 1))

print("完整路径:", full_path)
print("最短距离:", shortest_distance)
'''
