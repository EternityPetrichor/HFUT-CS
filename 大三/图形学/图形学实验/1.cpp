#include <ctime>
#include <cstdlib>
#include<stdlib.h>
#include<stdio.h>
#include<iostream>
// Simple round function for VC6.0

using namespace std;

int RoundFloat(float x)
{
    return static_cast<int>(x + 0.5);
}
void DrawPixel(int x, int y) {
    // ������ʵ�ֻ������صĴ���
    // Ϊ�����ܲ��ԣ����ǽ��䱣��Ϊ��
}
void DDA(int X0, int Y0, int X1, int Y1)
{
	//----------��ʵ��DDA�㷨------------//
 // Calculate dx and dy
    int dx = X1 - X0;
    int dy = Y1 - Y0;

    // Determine the number of steps needed
    int steps = abs(dx) > abs(dy) ? abs(dx) : abs(dy);

    // Calculate increment in x and y for each step
    float Xincrement = dx / static_cast<float>(steps);
    float Yincrement = dy / static_cast<float>(steps);

    // Initial point
    float X = X0;
    float Y = Y0;

    // Plot the initial point
    DrawPixel(RoundFloat(X), RoundFloat(Y));

    // Iterate through each step
    for (int i = 1; i <= steps; i++)
    {
        X += Xincrement;
        Y += Yincrement;

        // Plot the point
        DrawPixel(RoundFloat(X), RoundFloat(Y));
    }
}

void Mid_Bresenham(int X0, int Y0, int X1, int Y1)
{
	//-------��ʵ��Mid_Bresenham�㷨-------//
	// Calculate dx and dy
    int dx = X1 - X0;
    int dy = Y1 - Y0;

    // Determine the increments for x and y
    int Xincrement = dx > 0 ? 1 : -1;
    int Yincrement = dy > 0 ? 1 : -1;

    // Make dx and dy positive
    dx = abs(dx);
    dy = abs(dy);

    // Variables for decision parameter and initial values
    int D, X, Y;

    if (dx >= dy) // Slope <= 1
    {
        D = 2 * dy - dx;
        Y = Y0;

        // Iterate through each x
        for (X = X0; X != X1; X += Xincrement)
        {
            // Plot the point
            DrawPixel(X, Y);

            // Adjust the decision parameter
            if (D > 0)
            {
                Y += Yincrement;
                D -= 2 * dx;
            }
            D += 2 * dy;
        }
        DrawPixel(X, Y); // Plot the last point
    }
    else // Slope > 1
    {
        D = 2 * dx - dy;
        X = X0;

        // Iterate through each y
        for (Y = Y0; Y != Y1; Y += Yincrement)
        {
            // Plot the point
            DrawPixel(X, Y);

            // Adjust the decision parameter
            if (D > 0)
            {
                X += Xincrement;
                D -= 2 * dy;
            }
            D += 2 * dx;
        }
        DrawPixel(X, Y);// Plot the last point
    }
}


// ���ܲ��Ժ���
void TestPerformance()
{
    const int NUM_LINES = 100000; // ���Ե�ֱ������
    clock_t start, end;
    double ddaTotalTime = 0, bresenhamTotalTime = 0;

    // ���� DDA �㷨
    start = clock();
    for (int i = 0; i < NUM_LINES; i++)
    {
        // �����������
        int X0 = rand()%100;
        int Y0 = rand()%100;
        int X1 = rand()%100;
        int Y1 = rand()%100;

        // ���� DDA ����
        DDA(X0, Y0, X1, Y1);
    }
    end = clock();
    ddaTotalTime = static_cast<double>(end - start) / CLOCKS_PER_SEC;

    // ���� Mid_Bresenham �㷨
    start = clock();
    for (int i = 0; i < NUM_LINES; i++)
    {
        // �����������
         int X0 = rand()%100;
        int Y0 = rand()%100;
        int X1 = rand()%100;
        int Y1 = rand()%100;


        // ���� Mid_Bresenham ����
        Mid_Bresenham(X0, Y0, X1, Y1);
    }
    end = clock();
    bresenhamTotalTime = static_cast<double>(end - start) / CLOCKS_PER_SEC;


    cout << "DDA Total Time: " << ddaTotalTime << " seconds" << endl;
    cout << "Mid_Bresenham Total Time: " << bresenhamTotalTime << " seconds" << endl;
}

// ������
int main()
{
    // �������ܲ���
    TestPerformance();

    return 0;
}
