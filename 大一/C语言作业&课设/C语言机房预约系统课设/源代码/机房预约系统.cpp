#include<iostream>
#include<cstring>
#include<cstdlib>
#include<iomanip>
#include<cstdio>

using namespace std;

void Query();
void book_computer();
void book_exit();
void search_wait();
int statistical();
void info_empty();

struct PCinfo  //机位信息，用户信息
{
    int State[6];  //2小时为一个时间段，早上8点到下午8点，6个时间段，0表示空位，1表示无空位
    char name[10];
    char sex[30];
    char tel[20];
    int year;
    int month;
    int day;
    int start_time;
    int end_time;
    int stayinline;  //stayinline用来表示是否有等待信息，0表示无，1表示有
};
PCinfo info[100][100];
int Month[]={0,31,28,31,30,31,30,31,31,30,31,30,31};
int date[12][30];

int main(void)
{
    info_empty();  //每天重新启动系统时会清空机位信息
    while(true){
        system("cls");
        cout << "\t\t\t=================欢迎使用合肥工业大学机房预约系统=================" <<endl;
        cout << "\t\t\t\t ----------------------------------------------" << endl;
        cout << "\t\t\t\t|                                              |" << endl;
        cout << "\t\t\t\t|              1.查询机位信息                  |" << endl;
        cout << "\t\t\t\t|                                              |" << endl;
        cout << "\t\t\t\t|              2.机位预定                      |" << endl;
        cout << "\t\t\t\t|                                              |" << endl;
        cout << "\t\t\t\t|              3.退出预定                      |" << endl;
        cout << "\t\t\t\t|                                              |" << endl;
        cout << "\t\t\t\t|              4.查询等待信息                  |" << endl;
        cout << "\t\t\t\t|                                              |" << endl;
        cout << "\t\t\t\t|              0.退出系统                      |" << endl;
        cout << "\t\t\t\t|                                              |" << endl;
        cout << "\t\t\t\t ----------------------------------------------" << endl;

        int select;
        cout << "请输入要选择的功能:";
        cin >> select;

        switch(select)
        {
        case 1: Query();
            break;
        case 2: book_computer();
            break;
        case 3: book_exit();
            break;
        case 4: search_wait();
            break;
        case 0: cout << "退出成功!" << endl;
            return 0;
            break;
        default: cout << "输入有误，请重新选择！" <<endl;
        system("pause");
        system("cls");
            break;

        }
    }

    return 0;
}

void Query()
{
    system("cls");
    int year, month, day, start_time, end_time, time, a=1;
    int n=0;
    int i;
    while(true){
        cout << "\t\t\t*****************请输入时间来查看20个机位信息*****************" <<endl;
        cout << "\t\t\t\t请输入您想要查询的时间:(例如：2022/1/1/8-10表示)" <<endl;
        scanf("%d/%d/%d/%d-%d", &year, &month, &day, &start_time, &end_time);
        if((start_time-8)/2>=0 && (end_time-8)/2<=6){
            time = (start_time-8)/2;
        }
        else{
            cout << "输入时间有误！" <<endl;
            system("pause");
            return;
        }
        system("cls");
        if(month<1||month>12||day<0||day>Month[month]){
            cout << "输入时间错误！" <<endl;
            cout << "请重新输入指令" <<endl;
            cout << "\t\t\t\t ----------------------------------------------" << endl;
            cout << "\t\t\t\t|                1.重新输入                    |" << endl;
            cout << "\t\t\t\t|                                              |" << endl;
            cout << "\t\t\t\t|                2.返回菜单                    |" << endl;
            cout << "\t\t\t\t ----------------------------------------------" << endl;
            cin >> n;
            if(n==2){break;}
            if(n==1){break;}
        }
        else break;
    }
    if(n==2){return;}

    cout << "当前您要查询第几次预约的机位信息？" <<endl;
    cin >> a;
    for(i=1;i<=20;i++){
        if(date[month][day]==1&&info[i][a].State[time]==1){
            cout << "\t\t\t\t第" << i << "号计算机在该时间段已被预约" <<endl;
        }
        else {cout << "\t\t\t\t第" << i << "号计算机在该时间段未被预约" <<endl;}
    }
    system("pause");
}

void book_computer()
{
    system("cls");
    int year, month, day, start_time, end_time, time;
    int b=0, m=0;
    int i=0, n=0, j, k, s, t;
    while(true){
        cout << "\t\t\t*********请输入您想要查询的时间来查看是否有空位计算机:(例如：2022/1/1/8-10表示)*********" <<endl;
        cout << "\t\t提示：若有空闲机位系统将自动帮您预约，若无空位，则您可以选择最近时间段空位进行预约！" <<endl;
        scanf("%d/%d/%d/%d-%d", &year, &month, &day, &start_time, &end_time);

        if((start_time-8)/2>=0 && (end_time-8)/2<=6){
            time = (start_time-8)/2;
        }
        else{
            cout << "输入时间有误！" <<endl;
            system("pause");
            return;
        }
        system("cls");
        if(month<1||month>12||day<0||day>Month[month]){
            cout << "输入时间错误！" <<endl;
            cout << "请重新输入指令" <<endl;
            cout << "\t\t\t\t ----------------------------------------------" << endl;
            cout << "\t\t\t\t|                1.重新输入                    |" << endl;
            cout << "\t\t\t\t|                                              |" << endl;
            cout << "\t\t\t\t|                2.返回菜单                    |" << endl;
            cout << "\t\t\t\t ----------------------------------------------" << endl;
            cin >> n;
            if(n==2){break;}
            if(n==1){break;}
        }
        else break;
    }
    if(n==2){return;}
    int a=0;
    s=statistical();
    cout << "\t\t\t\t\t********请选择预约模式********" <<endl;
    cout << "\t\t\t\t ----------------------------------------------" << endl;
    cout << "\t\t\t\t|                1.顺序预约                    |" << endl;
    cout << "\t\t\t\t|                                              |" << endl;
    cout << "\t\t\t\t|                2.编号预约                    |" << endl;
    cout << "\t\t\t\t ----------------------------------------------" << endl;
    cin >> a;
    if(a==1){
        for(i=1;i<=20;i++){
            if(date[month][day]==0&&info[i][a].State[time]==0){
                date[month][day]=1;
                info[i][a].State[time]=1;
            cout << "\t\t\t=========第" << i << "号计算机在该时间段已成功预约=========" <<endl;
            cout << "\t\t\t=========请您在预约时间段内合理使用计算机=========" <<endl;
            cout << "\t\t\t本次为第" << s << "次预约";
            system("pause");
            return;
        }
    }
    cout << "您查询的时间段内无空闲机位！" <<endl;
    cout << "\t\t\t\t========是否仍在非空闲时间段内使用机位？========" <<endl;
    cout << "\t\t\t\t ----------------------------------------------" << endl;
    cout << "\t\t\t\t|                1.是(加入等待队列)            |" << endl;
    cout << "\t\t\t\t|                                              |" << endl;
    cout << "\t\t\t\t|                2.否(查找临近的空机位)        |" << endl;
    cout << "\t\t\t\t ----------------------------------------------" << endl;
    cin >> n;
    if(n==1){
        system("cls");
        cout << "请输入您想要等待的机位:";
        cin >> m;
        cout << "请输入您的姓名:";
        cin >> info[m][s].name;
        cout << "请输入您的姓别:";
        cin >> info[m][s].sex;
        cout << "请输入您的电话号码:";
        cin >> info[m][s].tel;
        info[m][s].year=year;
        info[m][s].month=month;
        info[m][s].day=day;
        info[m][s].start_time=start_time;
        info[m][s].end_time=end_time;
        info[m][s].stayinline=1;

        cout << "您已成功加入等待队列！";

        system("pause");
        return;
    }
    else{
        for(i=month;i<=12;i++){
            for(j=day;j<=Month[i];j++){
                for(k=1;k<=6;k++){
                    for(m=1;m<20;m++){
                        if(date[i][j]==0||info[m][s].State[time]==0){
                            printf("你可以预约到最近时间段的计算机是：第%d号,%d年%d月%d日%d时-%d时\n",m, year, i, j, (2*k+6), (2*k+8));
                            cout << "\t\t\t\t ----------------------------------------------" << endl;
                            cout << "\t\t\t\t|                1.预约该计算机                |" << endl;
                            cout << "\t\t\t\t|                                              |" << endl;
                            cout << "\t\t\t\t|                2.取消预约并退出              |" << endl;
                            cout << "\t\t\t\t ----------------------------------------------" << endl;
                            cin >> n;
                            if(n==1){
                                cout << "\t\t\t\t======预约成功！======";
                                date[i][j]=1;
                                info[m][s].State[time]=1;
                            }
                            system("pause");
                            return;
                        }
                    }
                }
            }
        }
    }
}
    if(a==2){
        system("cls");
        cout << "请输入您要预约的机号:";
        cin >> i;
        if(date[month][day]==0||info[i][s].State[time]==0){
            date[month][day]=1;
            info[i][s].State[time]=1;
            cout << "\t\t预约成功！";
            system("pause");
        }
        else{
            cout << "该机位已被预约！" <<endl;
            cout << "\t\t\t\t========是否仍在非空闲时间段内使用机位？========" <<endl;
            cout << "\t\t\t\t ----------------------------------------------" << endl;
            cout << "\t\t\t\t|                1.是(加入等待队列)            |" << endl;
            cout << "\t\t\t\t|                                              |" << endl;
            cout << "\t\t\t\t|                2.否(查找临近的空机位)        |" << endl;
            cout << "\t\t\t\t ----------------------------------------------" << endl;
            cin >> n;
            if(n==1){
                system("cls");
                cout << "请输入您想要等待的机位:";
                cin >> b;
                cout << "请输入您的姓名:";
                cin >> info[b][s].name;
                cout << "请输入您的姓别:";
                cin >> info[b][s].sex;
                cout << "请输入您的电话号码:";
                cin >> info[b][s].tel;
                info[b][s].year=year;
                info[b][s].month=month;
                info[b][s].day=day;
                info[b][s].start_time=start_time;
                info[b][s].end_time=end_time;
                info[b][s].stayinline=1;;

                cout << "您已成功加入等待队列！";

                system("pause");
                return;
                }

            for(i=month;i<=12;i++){
                for(j=day;j<=Month[i];j++){
                    for(k=1;k<=6;k++){
                        for(m=1;m<20;m++){
                            if(date[i][j]==0||info[m][s].State[time]==0){
                                printf("你可以预约到最近时间段的计算机是：第%d号,%d年%d月%d日%d时-%d时\n",m, year, i, j, (2*k+6), (2*k+8));
                                cout << "\t\t\t\t ----------------------------------------------" << endl;
                                cout << "\t\t\t\t|                1.预约该计算机                |" << endl;
                                cout << "\t\t\t\t|                                              |" << endl;
                                cout << "\t\t\t\t|                2.取消预约并退出              |" << endl;
                                cout << "\t\t\t\t ----------------------------------------------" << endl;
                                cin >> n;
                                if(n==1){
                                    cout << "\t\t\t\t======预约成功！======";
                                    date[i][j]=1;
                                    info[m][s].State[time]=1;
                                }
                                system("pause");
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}

void book_exit()
{
    system("cls");
    int year, month, day, start_time, end_time, time;
    int s=0, n=0;

    while(true){
        cout << "\t\t\t\t======取消您已预约的机位======" <<endl;
        system("pause");
        cout << "\t\t\t\t请输入您想取消预约的时间:(例如：2022/1/1/8-10表示)" <<endl;
        scanf("%d/%d/%d/%d-%d", &year, &month, &day, &start_time, &end_time);
        if((start_time-8)/2>=0 && (end_time-8)/2<=6){
            time = (start_time-8)/2;
        }
        else{
            cout << "输入时间有误！" <<endl;
            system("pause");
            return;
        }
        if(month<1||month>12||day<0||day>Month[month]){
            system("cls");
            cout << "输入时间错误！" <<endl;
            cout << "请重新输入指令" <<endl;
            cout << "\t\t\t\t ----------------------------------------------" << endl;
            cout << "\t\t\t\t|                1.重新输入                    |" << endl;
            cout << "\t\t\t\t|                                              |" << endl;
            cout << "\t\t\t\t|                2.返回菜单                    |" << endl;
            cout << "\t\t\t\t ----------------------------------------------" << endl;
            cin >> n;
            if(n==2){break;}
        }
        else break;
    }
    if(n==2){return;}
    cout << "请输入您要取消预约的机号";
    cin >> n;
    cout << "您要取消第几次预约的机位？";
    cin >> s;
    if(info[n][s].State[time]==1){
        info[n][s].State[time]=0;
        cout << "取消预约成功！";
    }
    else {
        cout << "在该时间段内您没有任何预约！";
    }
    system("pause");
}

void search_wait()
{
    system("cls");
    int year, month, day, start_time, end_time, time;
    int i, j, k, m, s;

    cout << "\t\t\t\t请输入您想要预约的时间:(例如：2022/1/1/8-10表示)" <<endl;
    scanf("%d/%d/%d/%d-%d", &year, &month, &day, &start_time, &end_time);
    if((start_time-8)/2>=0 && (end_time-8)/2<=6){
            time = (start_time-8)/2;
        }
    else{
        cout << "输入时间有误！" <<endl;
        system("pause");
        return;
        }
    if(month<1||month>12||day<0||day>Month[month]){
        cout << "输入时间错误！" <<endl;
        return;
        }
    cout << "请输入您要查询等待信息的机位号:";
    cin >> m;
    cout <<endl;
    cout << "\t\t\t\t======正在查询中，请稍后~~======" <<endl;
    printf("第%d号计算机:\n\n" ,m);
    printf("您准备查找第几次预约信息？\n\n");
    cin >> s;
    if(info[m][s].State[time]==0&&info[m][s].stayinline==0){
        cout << "无等待信息！" <<endl;
    }
    else{
        cout << "该机位在该时间段有等待成员！" <<endl;
        cout <<endl;
        for(i=month;i<=12;i++){
            for(j=day;j<=Month[i];j++){
                for(k=1;k<=6;k++){
                    for(m=1;m<20;m++){
                        if(date[i][j]==0||info[m][s].State[time]==0){
                            printf("你可以预约到最近时间段的计算机是：第%d号,%d年%d月%d日%d时-%d时\n",m, year, i, j, (2*k+6), (2*k+8));
                            system("pause");
                            return;
                        }
                    }
                }
            }
        }
    }
    system("pause");
    return;
}

int statistical()
{
    int n=0, s=1;
    int k;
    cout << "\t\t1.首次预约\n";
    cout << "\t\t2.再次预约\n";
    cin >> n;
    if(n==1){return s;}
    if(n==2){
        int i=0;
        cout << "\t\t\t\t ----------预约日期与前几次是否相同-----------" << endl;
        cout << "\t\t\t\t ----------------------------------------------" << endl;
        cout << "\t\t\t\t|                1.是                          |" << endl;
        cout << "\t\t\t\t|                                              |" << endl;
        cout << "\t\t\t\t|                2.否                          |" << endl;
        cout << "\t\t\t\t ----------------------------------------------" << endl;
        cin >> i;
        if(i==1){
            s=0;
            cout << "预约日期与第几次相同？" <<endl;
            cin >> k;
            s=s+k;
            return s;
        }
        if(i==2){
            s=0;
            cout << "本次为第几次预约？" <<endl;
            cin >> k;
            s=s+k;
            printf("本次为机房预约系统第%d次预约");
            return s;
        }
    }
}

void info_empty()
{
    int i, j, k, a;
    for(i=0;i<=20;i++){
        for(a=0;a<=20;a++){
            info[i][a].stayinline=0;
            for(j=0;j<=6;j++){
                info[i][a].State[j]=0;
            }
        }
    }
    for(j=0;j<=12;j++){
        for(k=0;k<=31;k++){
            date[j][k]=0;
        }
    }
}
