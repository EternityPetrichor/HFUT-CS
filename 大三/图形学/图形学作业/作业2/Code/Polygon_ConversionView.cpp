// Polygon_ConversionView.cpp : implementation of the CPolygon_ConversionView class
//

#include "stdafx.h"
#include "Polygon_Conversion.h"

#include "Polygon_ConversionDoc.h"
#include "Polygon_ConversionView.h"
#include "SettingDialog.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CPolygon_ConversionView

IMPLEMENT_DYNCREATE(CPolygon_ConversionView, CView)

BEGIN_MESSAGE_MAP(CPolygon_ConversionView, CView)
	//{{AFX_MSG_MAP(CPolygon_ConversionView)
	ON_WM_LBUTTONDOWN()
	ON_WM_RBUTTONDOWN()
	ON_COMMAND(ID_VIEW_INPUT_POLYGON, OnViewInputPolygon)
	ON_UPDATE_COMMAND_UI(ID_VIEW_INPUT_POLYGON, OnUpdateViewInputPolygon)
	ON_WM_MOUSEMOVE()
	ON_COMMAND(ID_VIEW_SETTING, OnViewSetting)
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CPolygon_ConversionView construction/destruction

CPolygon_ConversionView::CPolygon_ConversionView()
{
	m_bInputting_Polygon = FALSE;

	m_Vertex_Number = 0;   //顶点数目为0；

	m_Fill_Color     = RGB(255, 0,0);
	m_bFillPolygon   = TRUE;
	m_iFillAlgorithm = 0;     


	m_pDC = NULL;
}

CPolygon_ConversionView::~CPolygon_ConversionView()
{
}

BOOL CPolygon_ConversionView::PreCreateWindow(CREATESTRUCT& cs)
{
	// TODO: Modify the Window class or styles here by modifying
	//  the CREATESTRUCT cs

	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CPolygon_ConversionView drawing

void CPolygon_ConversionView::OnDraw(CDC* pDC)
{
	CPolygon_ConversionDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);


	m_pDC = pDC;
	// TODO: add draw code for native data here

	if ( this->m_bFillPolygon && this->m_Vertex_Number >= 3)
	{
		switch(this->m_iFillAlgorithm) {
		case 0: //x_scan_line
			X_Scan_Line_Conersion(m_Vertices, m_Vertex_Number);			
			break;
		case 1:
			Active_Edge_Table_Conersion(m_Vertices, m_Vertex_Number);			
			break;
		default:
			break;
		}
	}
	DrawPolygonOutline(pDC);  //绘制多边型边框

	m_pDC = NULL;
	CView::OnDraw(pDC);	
}

/////////////////////////////////////////////////////////////////////////////
// CPolygon_ConversionView printing

BOOL CPolygon_ConversionView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CPolygon_ConversionView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CPolygon_ConversionView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CPolygon_ConversionView diagnostics

#ifdef _DEBUG
void CPolygon_ConversionView::AssertValid() const
{
	CView::AssertValid();
}

void CPolygon_ConversionView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CPolygon_ConversionDoc* CPolygon_ConversionView::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CPolygon_ConversionDoc)));
	return (CPolygon_ConversionDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CPolygon_ConversionView message handlers

void CPolygon_ConversionView::OnLButtonDown(UINT nFlags, CPoint point) 
{
	if ( this->m_bInputting_Polygon)
	{
		m_Vertices[m_Vertex_Number][0] = point.x;
		m_Vertices[m_Vertex_Number][1] = point.y;
		m_Vertex_Number++;

		if ( m_Vertex_Number > 1)
		{
			CDC *pDC = this->GetDC();
			pDC->SetMapMode(R2_COPYPEN);
			pDC->MoveTo(m_Vertices[m_Vertex_Number-2][0],m_Vertices[m_Vertex_Number-2][1]);
			pDC->LineTo(m_Vertices[m_Vertex_Number-1][0],m_Vertices[m_Vertex_Number-1][1]);
			pDC->DeleteDC();
		}

		//For rubber drawing
		m_PrePoint = point;
	}
	
	CView::OnLButtonDown(nFlags, point);
}

void CPolygon_ConversionView::OnMouseMove(UINT nFlags, CPoint point) 
{
	if ( this->m_bInputting_Polygon  && this->m_Vertex_Number > 0 )
	{
		CDC *pDC = this->GetDC();
		
		pDC->SetROP2(R2_NOT);
		
		
		pDC->MoveTo(m_Vertices[m_Vertex_Number-1][0],
			m_Vertices[m_Vertex_Number-1][1]);
		pDC->LineTo(m_PrePoint);
		
		pDC->MoveTo(m_Vertices[m_Vertex_Number-1][0],
			m_Vertices[m_Vertex_Number-1][1]);
		pDC->LineTo(point);
		
		m_PrePoint = point;
	}
	
	CView::OnMouseMove(nFlags, point);
}

void CPolygon_ConversionView::OnRButtonDown(UINT nFlags, CPoint point) 
{


	if ( this->m_bInputting_Polygon )
	{
		m_bInputting_Polygon = FALSE;


		if ( m_Vertex_Number > 1)
		{
			CDC *pDC = this->GetDC();
			//擦除最后一条rubber line;
			pDC->SetROP2(R2_NOT);
			pDC->MoveTo(m_Vertices[m_Vertex_Number-1][0],m_Vertices[m_Vertex_Number-1][1]);
			pDC->LineTo(m_PrePoint);

			//封闭多边形
			pDC->SetMapMode(R2_COPYPEN);
			pDC->MoveTo(m_Vertices[m_Vertex_Number-1][0],m_Vertices[m_Vertex_Number-1][1]);
			pDC->LineTo(m_Vertices[0][0],m_Vertices[0][1]);
			pDC->DeleteDC();

		
		}
	}
	CView::OnRButtonDown(nFlags, point);
}

void CPolygon_ConversionView::OnViewInputPolygon() 
{
	this->m_bInputting_Polygon = TRUE;	
}

void CPolygon_ConversionView::OnUpdateViewInputPolygon(CCmdUI* pCmdUI) 
{
	pCmdUI->SetCheck(m_bInputting_Polygon);
	pCmdUI->Enable(!m_bInputting_Polygon);	
}

void CPolygon_ConversionView::OnViewSetting() 
{
	CSettingDialog SettingDialog(m_Fill_Color,
		                         m_bFillPolygon,
								 m_iFillAlgorithm);
	if ( SettingDialog.DoModal() == IDOK )
	{
		this->m_bFillPolygon  = SettingDialog.m_bFill_Polygon;
		this->m_Fill_Color    = SettingDialog.m_FillColor;
		this->m_iFillAlgorithm = SettingDialog.m_iAlgorithm;

		this->Invalidate();
	}
}


/***************************************************************
绘制多边形边框
****************************************************************/
void CPolygon_ConversionView::DrawPolygonOutline(CDC *pDC)
{
	if ( this->m_Vertex_Number == 0) 
	{
		return;
	}
	pDC->SetMapMode(R2_COPYPEN);
	pDC->MoveTo(m_Vertices[0][0], m_Vertices[0][1]);

	//如果处于输入状态，不需要连接最后一点和第一点，否则需要连接；
	int LastPoint = this->m_bInputting_Polygon ?  (m_Vertex_Number-1): m_Vertex_Number;
	for ( int i = 1; i <= LastPoint; i++ )
	{
		pDC->LineTo(m_Vertices[i%m_Vertex_Number][0],m_Vertices[i%m_Vertex_Number][1]);
	}
	return;
}

void CPolygon_ConversionView::DrawPixel(int x, int y)
{

	if ( m_pDC != NULL)
	{
		CPen *pNewPen = new CPen;
		pNewPen->CreatePen(PS_SOLID, 1, this->m_Fill_Color);
		CPen *pOldPen = m_pDC->SelectObject(pNewPen);
		
		m_pDC->MoveTo(x-1, y);
		m_pDC->LineTo(x, y);

		m_pDC->SelectObject(pOldPen);
		pNewPen->DeleteObject();
		delete pNewPen;
	}
}


/*********************************************************************
功能：X-扫描线转换算法
      
参数说明：
     vertices[][2]---顶点列表
     VertexNum    ---顶点数目
备注：
     DrawPixel(int x, int y) --绘制像素点(x, y)
**********************************************************************/
void  CPolygon_ConversionView::X_Scan_Line_Conersion(int Vertices[][2], int VertexNum)
{
    int min_value_y = INT_MAX, max_value_y = INT_MIN;
    int point_x[10];

    for (int i = 0; i < VertexNum; i++) 
    {
        if (Vertices[i][1] < min_value_y)
        {
            min_value_y = Vertices[i][1];
        }
        if (Vertices[i][1] > max_value_y)
        {
            max_value_y = Vertices[i][1];
        }
    }
    for (int ponit_y = min_value_y; ponit_y <= max_value_y; ponit_y++) 
    {
        int k = 0;
        for (int m = 0; m < VertexNum; m++) 
        {
            int j = (m + 1) % VertexNum;
            if ((Vertices[m][1] <= ponit_y && Vertices[j][1] > ponit_y) || (Vertices[j][1] <= ponit_y && Vertices[m][1] > ponit_y)) 
            {
                point_x[k++] = Vertices[m][0] + (ponit_y - Vertices[m][1]) * (Vertices[j][0] - Vertices[m][0]) / (Vertices[j][1] - Vertices[m][1]);
            }
        }
        for (int n = 0; n < k - 1; n++) 
        {
            for (int j = 0; j < k - n - 1; j++) 
            {
                if (point_x[j] > point_x[j + 1]) 
                {
                    int Intermediate = point_x[j];
                    point_x[j] = point_x[j + 1];
                    point_x[j + 1] = Intermediate;
                }
            }
        }
        for (int p = 0; p < k; p += 2)
        {
            for (int xi = min(point_x[p], point_x[p + 1]); xi <= max(point_x[p], point_x[p + 1]); xi++)
            {
                DrawPixel(xi, ponit_y);
            }
        }
    }
}




/*********************************************************************
功能：有效边表转换算法
	  
参数说明：
	 vertices[][2]---顶点列表
	 VertexNum    ---顶点数目
备注：
     DrawPixel(int x, int y) --绘制像素点(x, y)
**********************************************************************/
void CPolygon_ConversionView::Active_Edge_Table_Conersion(int PolygonVertices[][2], int VertexCount)
{
    typedef struct edge_table
    {
        float x;         // 当前 x 坐标
        float slope, ymax;  // 斜率和最大 y 坐标
        edge_table *next;        // 指向下一个节点的指针
    } AET, NET;

    int maxY = PolygonVertices[0][1], minY = PolygonVertices[0][1];
    int i;
    for (i = 1; i < VertexCount; i++)
    {
        if (PolygonVertices[i][1] < minY)
           minY = PolygonVertices[i][1];
        if (PolygonVertices[i][1] > maxY)
           maxY = PolygonVertices[i][1];
    }

    AET *pAET = new AET;
    pAET->next = NULL;

    NET **pNET = new NET *[maxY - minY + 1];
    for (i = 0; i <= maxY - minY; i++)
    {
        pNET[i] = new NET;
        pNET[i]->next = NULL;
    }

    for (i = minY; i <= maxY; i++)
    {
        for (int j = 0; j < VertexCount; j++)
            if (PolygonVertices[j][1] == i)
            {
				// 检查相邻顶点，构建新边表
				// 如果相邻顶点的 Y 坐标大于当前顶点，则该边为一条活动边
				// 计算斜率 rk 并加入新边表
				// 如果下一个顶点的 Y 坐标大于当前顶点，则该边为一条活动边
				// 计算斜率 rk 并加入新边表
                if (PolygonVertices[(j - 1 + VertexCount) % VertexCount][1] > PolygonVertices[j][1])
                {
                    NET *p = new NET;
                    p->x = PolygonVertices[j][0];
                    p->ymax = PolygonVertices[(j - 1 + VertexCount) % VertexCount][1];
                    p->slope = float(PolygonVertices[(j - 1 + VertexCount) % VertexCount][0] - PolygonVertices[j][0]) /
                            float(PolygonVertices[(j - 1 + VertexCount) % VertexCount][1] - PolygonVertices[j][1]);
                    p->next = pNET[i - minY]->next;
                    pNET[i - minY]->next = p;
                }

                if (PolygonVertices[(j + 1 + VertexCount) % VertexCount][1] > PolygonVertices[j][1])
                {
                    NET *p = new NET;
                    p->x = PolygonVertices[j][0];
                    p->ymax = PolygonVertices[(j + 1 + VertexCount) % VertexCount][1];
                    p->slope = float(PolygonVertices[(j + 1 + VertexCount) % VertexCount][0] - PolygonVertices[j][0]) /
                            float(PolygonVertices[(j + 1 + VertexCount) % VertexCount][1] - PolygonVertices[j][1]);
                    p->next = pNET[i - minY]->next;
                    pNET[i - minY]->next = p;
                }
            }
    }

    for (i = minY; i <= maxY; i++)
    {
		// 更新活性边表中各边的 X 坐标
		// 对活性边表进行排序
		// 删除活性边表中 ymax 等于当前 Y 坐标的边
		// 将新边表中的边加入活性边表，并按照 X 坐标排序
		// 填充扫描线之间的像素
        NET *p = pAET->next;
        while (p)
        {
            p->x = p->x + p->slope;
            p = p->next;
        }

        AET *tq = pAET;
        p = pAET->next;
        tq->next = NULL;
        while (p)
        {
            while (tq->next && p->x >= tq->next->x)
                tq = tq->next;
            NET *s = p->next;
            p->next = tq->next;
            tq->next = p;
            p = s;
            tq = pAET;
        }

        AET *q = pAET;
        p = q->next;
        while (p)
        {
            if (p->ymax == i)
            {
                q->next = p->next;
                delete p;
                p = q->next;
            }
            else
            {
                q = q->next;
                p = q->next;
            }
        }

        p = pNET[i - minY]->next;
        q = pAET;
        while (p)
        {
            while (q->next && p->x >= q->next->x)
                q = q->next;
            NET *s = p->next;
            p->next = q->next;
            q->next = p;
            p = s;
            q = pAET;
        }

        p = pAET->next;
        while (p && p->next)
        {
            for (float j = p->x; j <= p->next->x; j++)
                DrawPixel(static_cast<int>(j), i);
            p = p->next->next;
        }
    }

    return;
}





