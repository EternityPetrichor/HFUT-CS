import random
import numpy as np
import matplotlib.pyplot as plt

# 城市坐标
cities = np.array([[2, 6], [2, 4], [1, 3], [4, 6], [5, 5], [4, 4], [6, 4], [3, 2], [2, 1], [4, 3], [6, 1], [7, 5], [3, 4], [5, 3], [7, 2]])

# 计算旅行距离
def distance(city1, city2):
    return np.sqrt(np.sum((city1 - city2) ** 2))

# 计算染色体的适应度
def fitness(chromosome):
    distance_sum = 0
    for i in range(len(chromosome) - 1):
        distance_sum += distance(cities[chromosome[i]], cities[chromosome[i+1]])
    distance_sum += distance(cities[chromosome[-1]], cities[chromosome[0]])
    return 1 / distance_sum

# 初始化种群
def init_population(population_size, chromosome_length):
    population = []
    for i in range(population_size):
        chromosome = list(range(chromosome_length))
        random.shuffle(chromosome)
        population.append(chromosome)
    return population

# 选择操作
def selection(population, fitness_values):
    fitness_sum = sum(fitness_values)
    probabilities = [fitness / fitness_sum for fitness in fitness_values]
    selected_indices = np.random.choice(len(population), size=len(population), replace=True, p=probabilities)
    return [population[i] for i in selected_indices]

# 交叉操作
def crossover(parent1, parent2):
    crossover_point1 = random.randint(0, len(parent1) - 1)
    crossover_point2 = random.randint(0, len(parent1) - 1)
    if crossover_point1 > crossover_point2:
        crossover_point1, crossover_point2 = crossover_point2, crossover_point1
    child1 = [-1] * len(parent1)
    child2 = [-1] * len(parent1)
    for i in range(crossover_point1, crossover_point2+1):
        child1[i] = parent1[i]
        child2[i] = parent2[i]
    j = 0
    k = 0
    for i in range(len(parent1)):
        if parent2[i] not in child1:
            while child1[j] != -1:
                j += 1
            child1[j] = parent2[i]
        if parent1[i] not in child2:
            while child2[k] != -1:
                k += 1
            child2[k] = parent1[i]
    return child1, child2

# 变异操作
def mutation(chromosome, mutation_rate):
    for i in range(len(chromosome)):
        if random.random() < mutation_rate:
            j = random.randint(0, len(chromosome) - 1)
            chromosome[i], chromosome[j] = chromosome[j], chromosome[i]
    return chromosome

# 遗传算法处理TSP问题
def tsp_ga(population_size, chromosome_length, generations, crossover_rate, mutation_rate):
    population = init_population(population_size, chromosome_length)
    fitness_values = [fitness(chromosome) for chromosome in population]
    best_fitness = max(fitness_values)
    best_chromosome = population[fitness_values.index(best_fitness)]
    for i in range(generations):
        parents = selection(population, fitness_values)
        offspring = []
        for j in range(0, len(parents), 2):
            if random.random() < crossover_rate:
                child1, child2 = crossover(parents[j], parents[j+1])
                offspring.append(child1)
                offspring.append(child2)
            else:
                offspring.append(parents[j])
                offspring.append(parents[j+1])
        population = offspring
        for j in range(len(population)):
            population[j] = mutation(population[j], mutation_rate)
        fitness_values = [fitness(chromosome) for chromosome in population]
        if max(fitness_values) > best_fitness:
            best_fitness = max(fitness_values)
            best_chromosome = population[fitness_values.index(best_fitness)]
        print("Generation:", i+1, "Best fitness:", best_fitness)
    return best_chromosome, best_fitness

# 运行遗传算法
best_chromosome, best_fitness = tsp_ga(population_size=100, chromosome_length=len(cities), generations=100, crossover_rate=0.8, mutation_rate=0.02)

# 绘制最优路径
best_path = np.concatenate((cities[best_chromosome], [cities[best_chromosome[0]]]))
plt.plot(best_path[:,0], best_path[:,1], 'o-')
plt.show()
