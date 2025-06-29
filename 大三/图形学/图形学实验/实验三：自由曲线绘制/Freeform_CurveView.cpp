// Freeform_CurveView.cpp : implementation of the CFreeform_CurveView class
//

#include "stdafx.h"
#include "Freeform_Curve.h"

#include "Freeform_CurveDoc.h"
#include "Freeform_CurveView.h"
#include "SettingDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CFreeform_CurveView

IMPLEMENT_DYNCREATE(CFreeform_CurveView, CView)

BEGIN_MESSAGE_MAP(CFreeform_CurveView, CView)
	//{{AFX_MSG_MAP(CFreeform_CurveView)
	ON_WM_LBUTTONUP()
	ON_COMMAND(ID_FUNCTION_INPUT, OnFunctionInput)
	ON_UPDATE_COMMAND_UI(ID_FUNCTION_INPUT, OnUpdateFunctionInput)
	ON_WM_RBUTTONUP()
	ON_WM_LBUTTONDOWN()
	ON_COMMAND(ID_FUNCTION_SETTING, OnFunctionSetting)
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CFreeform_CurveView construction/destruction

CFreeform_CurveView::CFreeform_CurveView()
{

	this->m_Point_Num             = 0;

	this->m_Point_Size            = 8;
	this->m_Point_Color           = RGB(255, 0, 0);
	this->m_ControlPolygon_Color = RGB(0, 0, 255);


	this->m_bShow_ControlPolygon = TRUE;
	this->m_bShow_ControlPoint    = TRUE;
	this->m_bInputting            = FALSE;

	this->m_CurveTypes[0] = this->m_CurveTypes[2] = this->m_CurveTypes[4] = 0; //均匀样条
	this->m_CurveTypes[1] = this->m_CurveTypes[3] = this->m_CurveTypes[5] = 1; //开放均匀样条

	this->m_Curve_Ranks[0] = this->m_Curve_Ranks[1] = 2;       //2阶曲线
	this->m_Curve_Ranks[2] = this->m_Curve_Ranks[3] = 3;       //3阶曲线
	this->m_Curve_Ranks[4] = this->m_Curve_Ranks[5] = 4;       //4阶曲线
	
	this->m_nWidth     = 1;
	for ( int i = 0; i < 6; i++)
	{
		this->m_bShowCurves[i] = true;
	}

	

	this->m_CurveColors[0] = RGB(255, 000, 000);
	this->m_CurveColors[1] = RGB(000, 255, 000);
	this->m_CurveColors[2] = RGB(000, 000, 255);
	this->m_CurveColors[3] = RGB(255, 0, 000);
	this->m_CurveColors[4] = RGB(000, 255, 255);
	this->m_CurveColors[5] = RGB(255, 000, 255);


	this->m_pDC = NULL;
}

CFreeform_CurveView::~CFreeform_CurveView()
{

}

BOOL CFreeform_CurveView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CFreeform_CurveView drawing

void CFreeform_CurveView::OnDraw(CDC* pDC)
{
	CFreeform_CurveDoc* pDoc = GetDocument();

	this->m_pDC = pDC;

	if ( this->m_bShow_ControlPoint )
	{
		this->DrawAllControlPoints();
	}
	if ( this->m_bShow_ControlPolygon )
	{
		this->DrawControlPolygon(pDC);
	}

	if ( !this->m_bInputting)
	{
		for ( int i = 2; i < 4; i++)
		{
			if ( m_bShowCurves[i] )
			{
				this->BSpline(this->m_Curve_Ranks[i], 
					          this->m_CurveTypes[i],
							  this->m_nWidth,
							  this->m_CurveColors[i]);
			}
		}
	}
	ASSERT_VALID(pDoc);

	this->m_pDC = NULL;
}

/////////////////////////////////////////////////////////////////////////////
// CFreeform_CurveView printing

BOOL CFreeform_CurveView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CFreeform_CurveView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CFreeform_CurveView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CFreeform_CurveView diagnostics

#ifdef _DEBUG
void CFreeform_CurveView::AssertValid() const
{
	CView::AssertValid();
}

void CFreeform_CurveView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CFreeform_CurveDoc* CFreeform_CurveView::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CFreeform_CurveDoc)));
	return (CFreeform_CurveDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CFreeform_CurveView message handlers



void CFreeform_CurveView::DrawPoint(int x, int y)
{
	CDC *pDC = this->GetDC();

	CPen   *pNewPen   = new CPen;
	CBrush *pNewBrush = new CBrush;
	pNewPen->CreatePen(PS_SOLID,1, this->m_Point_Color);
	pNewBrush->CreateSolidBrush(this->m_Point_Color);

	CPen   *pOldPen    = pDC->SelectObject(pNewPen);
	CBrush *pOldBrush  = pDC->SelectObject(pNewBrush);

	int x1 = x - this->m_Point_Size/2;
	int x2 = x + this->m_Point_Size/2;
	int y1 = y - this->m_Point_Size/2;
	int y2 = y + this->m_Point_Size/2;


	pDC->Ellipse(x1,y1, x2,y2);

	pDC->SelectObject(pOldPen);
	pDC->SelectObject(pOldBrush);

	pNewPen->DeleteObject();
	pNewBrush->DeleteObject();

	
	delete pNewPen;
	delete pNewBrush;
	
	pDC->DeleteDC();

	return;
}


void CFreeform_CurveView::DrawSegment(int x0, int y0, int x1, int y1, CPen *pNewPen)
{
	if ( this->m_pDC  == NULL || pNewPen == NULL  )
	{
		return;
	}

	CPen *pOldPen = this->m_pDC->SelectObject(pNewPen);

	this->m_pDC->MoveTo(x0, y0);
	this->m_pDC->LineTo(x1, y1);

	this->m_pDC->SelectObject(pOldPen);    // To restore the old pen
}


void CFreeform_CurveView::DrawControlPolygon(CDC *pDC)
{
	if ( !this->m_bShow_ControlPolygon)
	{
		return;
	}

	if ( this->m_Point_Num <= 0)
	{
		return;
	}

	CPen *pNewPen = new CPen;
	pNewPen->CreatePen(PS_DASHDOT,1, this->m_ControlPolygon_Color);
	
	CPen *pOldPen = pDC->SelectObject(pNewPen);

	

	pDC->MoveTo(this->m_Points[0][0], this->m_Points[0][1]);
	for ( int i =0; i < this->m_Point_Num; i++)
	{
		pDC->LineTo(this->m_Points[i][0], this->m_Points[i][1]);
	}

	pDC->SelectObject(pOldPen);

	pNewPen->DeleteObject();
	delete pNewPen;

}

void CFreeform_CurveView::DrawAllControlPoints()
{
	if ( !this->m_bShow_ControlPoint)
	{
		return;
	}
	for (int i = 0; i < this->m_Point_Num; i++)
	{
		this->DrawPoint(this->m_Points[i][0], this->m_Points[i][1]);
	}
	return;
}

void CFreeform_CurveView::OnLButtonUp(UINT nFlags, CPoint point) 
{
	if ( this->m_bInputting )
	{
		if ( this->m_Point_Num < MAX_POINT_NUMBER)
		{
			DrawPoint(point.x, point.y);
			this->m_Points[this->m_Point_Num][0] = point.x;
			this->m_Points[this->m_Point_Num][1] = point.y;
			this->m_Point_Num++;
		}
	}
	CView::OnLButtonUp(nFlags, point);
}


void CFreeform_CurveView::OnRButtonUp(UINT nFlags, CPoint point) 
{
	
	if ( this->m_bInputting )
	{
		this->m_bInputting = FALSE;
		this->Invalidate();
	}
	
	CView::OnRButtonUp(nFlags, point);
}


void CFreeform_CurveView::OnFunctionInput() 
{
	this->m_bInputting = TRUE;
	this->m_Point_Num = 0;
	this->Invalidate();		
}

void CFreeform_CurveView::OnUpdateFunctionInput(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(this->m_bInputting);
	pCmdUI->Enable(!this->m_bInputting);
}



void CFreeform_CurveView::BSpline( int m , int nSplineType, int nWidth, COLORREF clr)
{
	if ( m <1  || m > MAX_RANK || this->m_Point_Num < m)
	{
		return;
	}

	float nodes[MAX_POINT_NUMBER + MAX_RANK + 1];
	int   n = this->m_Point_Num - 1;
	
	if ( !Create_Nodes_Vector(n, m, nSplineType, nodes) )
	{
		return;
	}

	this->DrawSplineCurve(n, m, nodes, 100, nWidth, clr);
	return;
}


//这个函数的目的是为了确保在调用除法运算时，不会遇到除以零的错误。在数值计算中
//，处理接近于零的数通常是一个重要的问题，因为直接除以零会导致未定义行为（通常是程序崩溃或返回特殊值）。
float CFreeform_CurveView::Divide(float l_operand, float r_operand)
{
	if ( /*fabs(l_operand) < 1.0e-6  && */fabs(r_operand) < 1.0e-6 )
	{
		return 0;
	}
	else
	{
		return l_operand / r_operand;
	}
}



void CFreeform_CurveView::DrawSplineCurve(int n, int m, float nodes[], int SamplePointNumber, int LineWidth, COLORREF LineColor)
{

	float interval = (nodes[ n + 1]-nodes[ m - 1]) / ( SamplePointNumber - 1 );
	
	float x0 = 0, y0 = 0;
	float x1, y1;
	
	//To get x0 and y0;
	x0 = 0.0; 		y0 = 0.0;
	float t = nodes[m-1];
	for ( int k = 0; k <= n; k++)
	{
		float val = BKM(t, k, m, nodes);

		x0 += this->m_Points[k][0]*val;
		y0 += this->m_Points[k][1]*val;
	}

	CPen *pNewPen = new CPen;
	pNewPen->CreatePen(PS_SOLID, LineWidth, LineColor);
	
	
	int i ;	
	for ( i = 1, t = nodes[m-1]+interval; i < SamplePointNumber; i++, t += interval )
	{
		x1 = 0.0; 		y1 = 0.0;
		//因为曲线的定义域是开区间,所以对对最后一个点要进行特殊处理
		if ( i == SamplePointNumber -1 )
		{
			t -= interval*0.01;
		}

		for ( int k = 0; k <= n; k++)
		{
			float val = BKM(t, k, m, nodes);
			x1 += this->m_Points[k][0]*val;
			y1 += this->m_Points[k][1]*val;
		}
		
		DrawSegment(x0, y0, x1, y1, pNewPen);
		x0 = x1; y0 = y1;
	}

	pNewPen->DeleteObject();
	delete pNewPen;
	return;
}

void CFreeform_CurveView::OnFunctionSetting() 
{
	CSettingDlg SettingDlg(this->m_Point_Size,
		                   this->m_Point_Color,
						   this->m_bShow_ControlPoint,

						   this->m_bShow_ControlPolygon,
						   this->m_ControlPolygon_Color,

						   this->m_nWidth,
						   this->m_bShowCurves,
						   this->m_CurveColors);

	if ( SettingDlg.DoModal() == IDOK )
	{
		this->m_Point_Size   = SettingDlg.m_nControlPoint_Size;
		this->m_Point_Color  = SettingDlg.m_ControlPoint_Color;
		this->m_bShow_ControlPoint = SettingDlg.m_bShowControlPoint;

		this->m_bShow_ControlPolygon = SettingDlg.m_bShowControlPolygon;
		this->m_ControlPolygon_Color = SettingDlg.m_ControlPolygon_Color;
		
		this->m_nWidth       = SettingDlg.m_nCurveWidth;
		for ( int i = 0; i < 6; i++)
		{
			this->m_bShowCurves[i] = SettingDlg.m_bShowCurves[i];
			this->m_CurveColors[i] = SettingDlg.m_CurveColors[i];	
		}
		Invalidate();
	}
}


/******************************************************************
功能：输入代码实现Bspline函数的基函数

提示：(1) 参考P231页的BSpline曲线的基函数
      (2) 采用递归的方法实现
	  (3) 调用 float Divide( float l_Operand, float r_Operand)实现除法，
	      Divide函数处理了“除数为0”的情况

参数解释：
      t,k,m----参考BSpline函数的定义
	  nodes----节点矢量(t0, t1, t2, t3......);    
  
*******************************************************************/
/*float CFreeform_CurveView::BKM
			(float t, int k, int m, float nodes[])
{
    float value = 0.0;
	// 如果阶数m为0，基函数就退化成一个简单的区间判断  
	if (m == 0) {
        return (t >= nodes[k] && t < nodes[k + 1]) ? 1.0f : 0.0f;
    }

    // 计算左侧权重
	// (t - nodes[k]) 是t到当前区间左端点的距离  
    // (nodes[k + m] - nodes[k]) 是当前区间的宽度  
    // 通过Divide函数来避免除以0的情况
    float left = (t - nodes[k]) * Divide(1.0f, nodes[k + m] - nodes[k]);
    // 计算右侧权重
	// 计算右侧权重  
    // (nodes[k + m + 1] - t) 是t到当前区间右端点的距离  
    // (nodes[k + m + 1] - nodes[k + 1]) 是下一个区间的宽度  
    // 同样使用Divide函数来避免除以0的情况 
    float right = (nodes[k + m + 1] - t) * Divide(1.0f, nodes[k + m + 1] - nodes[k + 1]);

    // 递归计算左侧和右侧的值
	// 递归调用BKM函数来计算左侧和右侧的基函数值  
    // 左侧调用时，阶数减少1，索引k保持不变  
    // 右侧调用时，阶数减少1，索引k增加1  
    // 最后将左侧和右侧的基函数值分别乘以对应的权重，并相加得到当前点的基函数值 
    return left * BKM(t, k, m - 1, nodes) + right * BKM(t, k + 1, m - 1, nodes);


	return value;
}*/

float CFreeform_CurveView::BKM(float t, int k, int m, float nodes[])  
{  
    // 初始化基函数值为0.0  
    float value = 0.0;  
  
    // 当m等于1时，表示是最低次的情况，这时候基函数是一个阶梯函数  
    if(m==1)  
    {  
        // 如果t大于等于当前节点k，且小于下一个节点k+1，则基函数值为1  
        if(t>=nodes[k]&&t<nodes[k+1])  
            value = 1;  
        // 否则基函数值为0  
        else  
            value = 0;  
    }  
    // 当m大于1时，基函数通过递归调用BKM函数并结合一定的系数计算得到  
    else if(m>1)  
    {  
        // 根据B样条基函数的递归公式计算value  
        value = (Divide((t-nodes[k]),(nodes[k+m-1]-nodes[k]))*BKM(t,k,m-1,nodes)  
                + Divide((nodes[k+m]-t),(nodes[k+m]-nodes[k+1]))*BKM(t,k+1,m-1,nodes));  
    }  
  
    // 返回计算得到的基函数值  
    return value;  
}


/******************************************************************
功能：生成曲线的节点矢量


参数解释：
      n, m ----------参考BSpline函数的定义

	  SplineTyple----表示BSpline曲线类型, 
	                 0--均匀BSpline曲线，1--开放均匀BSpline曲线,
					 其它----非法参数

	  nodes----------用于保存节点矢量
	  nodesNumber----用于保存节点的个数  
*******************************************************************/
//为节点矢量赋值
bool CFreeform_CurveView::Create_Nodes_Vector(int    n, 
											  int    m, 
											  int    SplineType, 
											  float  nodes[])
{
	switch(SplineType) {
	case 0: //均匀B样条曲线
		{
			//添加代码......................................
			//这里按照大多数情况为均匀B样条曲线的节点矢量赋值，从0开始以1为间距递增
			for(int i = 0; i < n+m; i++)
				nodes[i] = i;
			break;
		}

	case 1: //开放均匀B样条曲线
		{
			//添加代码......................................
			//
			int i = 0;
			for(;i<m;i++)
				nodes[i] = 0;//0<=i<=m，赋值0，前m个点赋值为0
			for(;i<=n;i++)
				nodes[i] = i - m + 1;//m<=i<=n，赋值i-m+1
			for(;i<=n+m;i++)
				nodes[i]=n-m+2;//n<=i，赋值n-m+2
			//然后在最后m个点赋值为n-m+2。
			break;
		}
	default:
		return false;
	}
	return true;
}
