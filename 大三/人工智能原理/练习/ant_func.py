import math
import random
import pandas as pd
import matplotlib.pyplot as plt
from matplotlib.pylab import mpl
 
# 添加这条可以让图形显示中文，字体显示为黑体
mpl.rcParams['font.sans-serif'] = ['SimHei']
 
 
 
# 适应度的计算
def calFitness(line, dis_matrix):
    # 贪婪策略得到距离矩阵（解码过程）
    # 计算路径距离（评价函数）
    dis_sum = 0  # 路线距离
    dis = 0
    for i in range(len(line)):
        if i < len(line) - 1:
            # 依次计录一个数以及下一个数的距离，存入城市间的距离矩阵
            dis = dis_matrix.loc[line[i], line[i + 1]]
            dis_sum = dis_sum + dis
        else:
            # 最后一个数，无下一个数的情况
            dis = dis_matrix.loc[line[i], line[0]]
            dis_sum = dis_sum + dis
    # 返回城市间的路线距离矩阵
    return round(dis_sum, 1)
 
 
# 联赛选择算子
def tournament_select(pops, popsize, fits, tournament_size):
    new_pops, new_fits = [], []
    # 步骤1 从群体中随机选择M个个体，计算每个个体的目标函数值
    while len(new_pops) < len(pops):
        tournament_list = random.sample(range(0, popsize), tournament_size)
        tournament_fit = [fits[i] for i in tournament_list]
        # 转化为df方便索引
        tournament_df = pd.DataFrame \
            ([tournament_list, tournament_fit]).transpose().sort_values(by=1).reset_index(drop=True)
        # 步骤2 根据每个个体的目标函数值，计算其适应度
        fit = tournament_df.iloc[0, 1]
        pop = pops[int(tournament_df.iloc[0, 0])]
        # 步骤3 选择适应度最大的个体
        new_pops.append(pop)
        new_fits.append(fit)
    return new_pops, new_fits
 
 
# 交叉算子
def crossover(popsize, parent1_pops, parent2_pops, pc):
    child_pops = []
    for i in range(popsize):
        # 初始化
        child = [None] * len(parent1_pops[i])
        parent1 = parent1_pops[i]
        parent2 = parent2_pops[i]
        if random.random() >= pc:
            child = parent1.copy()  # 随机生成一个（或者随机保留父代中的一个）
            random.shuffle(child)
        else:
            # parent1
            start_pos = random.randint(0, len(parent1) - 1)
            end_pos = random.randint(0, len(parent1) - 1)
            if start_pos > end_pos:
                tem_pop = start_pos
                start_pos = end_pos
                end_pos = tem_pop
            child[start_pos:end_pos + 1] = parent1[start_pos:end_pos + 1].copy()
            # parent2 -> child
            list1 = list(range(end_pos + 1, len(parent2)))
            list2 = list(range(0, start_pos))
            list_index = list1 + list2
            j = -1
            for i in list_index:
                for j in range(j + 1, len(parent2)):
                    if parent2[j] not in child:
                        child[i] = parent2[j]
                        break
        child_pops.append(child)
    return child_pops
 
 
# 变异操作
def mutate(pops, pm):
    pops_mutate = []
    for i in range(len(pops)):
        pop = pops[i].copy()
        # 随机多次成对变异
        # 随机选出两个位置进行交换
        t = random.randint(1, 5)
        count = 0
        while count < t:
            if random.random() < pm:
                mut_pos1 = random.randint(0, len(pop) - 1)
                mut_pos2 = random.randint(0, len(pop) - 1)
                #如果不相等则进行取反的操作，这里使用交换
                if mut_pos1 != mut_pos2:
                    tem = pop[mut_pos1]
                    pop[mut_pos1] = pop[mut_pos2]
                    pop[mut_pos2] = tem
            pops_mutate.append(pop)
            count += 1
    return pops_mutate
 
 
# 画路径图
def draw_path(line, CityCoordinates):
    x, y = [], []
    for i in line:
        Coordinate = CityCoordinates[i]
        x.append(Coordinate[0])
        y.append(Coordinate[1])
    x.append(x[0])
    y.append(y[0])
    plt.plot(x, y, 'r-', color='#FF3030', alpha=0.8, linewidth=2.2)
    plt.xlabel('x')
    plt.ylabel('y')
    plt.show()
 
 
if __name__ == '__main__':
    # 参数
    CityNum = 15  # 城市数量
    MinCoordinate = 0  # 二维坐标最小值
    MaxCoordinate = 10  # 二维坐标最大值
    # GA参数
    generation = 100 # 迭代次数
    popsize = 100  # 种群大小
    tournament_size = 5  # 锦标赛小组大小
    pc = 0.95  # 交叉概率
    pm = 0.1  # 变异概率
 
    # 随机生成城市的坐标,城市序号为0,1,2,3...直到CityNum的数目20
    CityCoordinates = \
        [(random.randint(MinCoordinate, MaxCoordinate), random.randint(MinCoordinate, MaxCoordinate)) for
         i in range(CityNum)]
    # 计算城市之间的距离
    dis_matrix = \
        pd.DataFrame(data=None, columns=range(len(CityCoordinates)), index=range(len(CityCoordinates)))
    for i in range(len(CityCoordinates)):
        xi, yi = CityCoordinates[i][0], CityCoordinates[i][1]
        for j in range(len(CityCoordinates)):
            xj, yj = CityCoordinates[j][0], CityCoordinates[j][1]
            dis_matrix.iloc[i, j] = round(math.sqrt((xi - xj) ** 2 + (yi - yj) ** 2), 2)
 
    iteration = 0
    # 初始化,随机构造
    pops = \
        [random.sample([i for i in list(range(len(CityCoordinates)))], len(CityCoordinates)) for
         j in range(popsize)]
    #画出随机得到的城市连接图
    draw_path(pops[i], CityCoordinates)
    # 计算适应度
    fits = [None] * popsize
    for i in range(popsize):
        fits[i] = calFitness(pops[i], dis_matrix)
    # 保留当前最优,最小的fits为最优解
    best_fit = min(fits)
    best_pop = pops[fits.index(best_fit)]
 
    print('初代最优值 %.1f' % (best_fit))
    best_fit_list = []
    best_fit_list.append(best_fit)
 
    while iteration <= generation:
        # 锦标赛赛选择
        pop1, fits1 = tournament_select(pops, popsize, fits, tournament_size)
        pop2, fits2 = tournament_select(pops, popsize, fits, tournament_size)
        # 交叉
        child_pops = crossover(popsize, pop1, pop2, pc)
        # 变异
        child_pops = mutate(child_pops, pm)
        # 计算子代适应度
        child_fits = [None] * popsize
        for i in range(popsize):
            child_fits[i] = calFitness(child_pops[i], dis_matrix)
            # 一对一生存者竞争
        for i in range(popsize):
            if fits[i] > child_fits[i]:
                fits[i] = child_fits[i]
                pops[i] = child_pops[i]
 
        if best_fit > min(fits):
            best_fit = min(fits)
            best_pop = pops[fits.index(best_fit)]
 
        best_fit_list.append(best_fit)
 
        print('第%d代最优值 %.1f' % (iteration, best_fit))
        iteration += 1
 
    # 路径顺序
    print(best_pop)
 
    draw_path(best_pop, CityCoordinates)
