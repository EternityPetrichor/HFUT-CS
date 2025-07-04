// IlluminationView.cpp : implementation of the CIlluminationView class
//

#include "stdafx.h"
#include "Illumination.h"
#include "math.h"

#include "IlluminationDoc.h"
#include "IlluminationView.h"

#include "Global_Funcs.h"
#include "SettingDialog.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CIlluminationView

IMPLEMENT_DYNCREATE(CIlluminationView, CView)

BEGIN_MESSAGE_MAP(CIlluminationView, CView)
	//{{AFX_MSG_MAP(CIlluminationView)
	ON_WM_SIZE()
	ON_COMMAND(ID_FUNCTION_SETTING, OnFunctionSetting)
	ON_WM_ERASEBKGND()
	//}}AFX_MSG_MAP
	// Standard printing commands
	ON_COMMAND(ID_FILE_PRINT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_DIRECT, CView::OnFilePrint)
	ON_COMMAND(ID_FILE_PRINT_PREVIEW, CView::OnFilePrintPreview)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CIlluminationView construction/destruction

CIlluminationView::CIlluminationView()
{
	//几何模型参数
	m_nLongitude_Grids   = 24;
	m_nLatitude_Grids    = 5;
	m_Ka                 = 0.4;      // Ambient Reflection Coefficient
	m_Kd                 = 0.8;      // Diffuse Reflection Coefficient

	//网格线参数
	m_nGridLineThickness = 1;
	m_GridLineColor      = RGB(255,255,255);
	
	//环境光参数
	m_Ia = 0.8;            // Ambient Light Intensity;
	m_AmbientLightColor = RGB(0,0,255);
	
	//漫反射光参数
	m_nShadingMode       = 2;
	m_Id                 = 1.0; //Diffuse light Intensity;
	m_DiffuseLightColor  = RGB(0,255,0);;  //Diffuse Light Color
	m_fLen     = 7.5;
	m_fAngleToXOY        = PI/180*75;  //75 degree     
	m_fAngleOnXOYProjectionToX = PI/3; //60 degree

	//显示控制参数
	m_bShowGridLine      = FALSE;
	m_bAmbientLight     = TRUE;
	m_bDiffuseLight      = TRUE;
}

CIlluminationView::~CIlluminationView()
{
}

BOOL CIlluminationView::PreCreateWindow(CREATESTRUCT& cs)
{
	return CView::PreCreateWindow(cs);
}

/////////////////////////////////////////////////////////////////////////////
// CIlluminationView drawing

void CIlluminationView::OnDraw(CDC* pDC)
{
	CIlluminationDoc* pDoc = GetDocument();
	ASSERT_VALID(pDoc);
	// TODO: add draw code for native data here
	this->Rendering(pDC);
	this->DrawEdges(pDC);
}

/////////////////////////////////////////////////////////////////////////////
// CIlluminationView printing

BOOL CIlluminationView::OnPreparePrinting(CPrintInfo* pInfo)
{
	// default preparation
	return DoPreparePrinting(pInfo);
}

void CIlluminationView::OnBeginPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add extra initialization before printing
}

void CIlluminationView::OnEndPrinting(CDC* /*pDC*/, CPrintInfo* /*pInfo*/)
{
	// TODO: add cleanup after printing
}

/////////////////////////////////////////////////////////////////////////////
// CIlluminationView diagnostics

#ifdef _DEBUG
void CIlluminationView::AssertValid() const
{
	CView::AssertValid();
}

void CIlluminationView::Dump(CDumpContext& dc) const
{
	CView::Dump(dc);
}

CIlluminationDoc* CIlluminationView::GetDocument() // non-debug version is inline
{
	ASSERT(m_pDocument->IsKindOf(RUNTIME_CLASS(CIlluminationDoc)));
	return (CIlluminationDoc*)m_pDocument;
}
#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CIlluminationView message handlers


void CIlluminationView::OnFunctionSetting() 
{
	CSettingDialog Dlg;

	//初始化参数
	//================几何模型参数========================
	Dlg.m_nLongitude_Grids   = m_nLongitude_Grids;
	Dlg.m_nLatitude_Grids    = m_nLatitude_Grids;
	Dlg.m_Ka                 = m_Ka;
	Dlg.m_Kd=m_Kd;
	
	//网格线参数
	Dlg.m_nGridLineThickness = m_nGridLineThickness;
	Dlg.m_GridLineColor      = m_GridLineColor;
	
	//环境光参数
	Dlg.m_Ia                 = m_Ia;
	Dlg.m_Id                 = m_Id;
	Dlg.m_AmbientLightColor = m_AmbientLightColor;
	
	//漫反射光参数
	Dlg.m_nShadingMode       = m_nShadingMode;
	Dlg.m_fLen               = m_fLen;
	Dlg.m_fAngleToXOY        = m_fAngleToXOY;
	Dlg.m_fAngleOnXOYProjectionToX= m_fAngleOnXOYProjectionToX;
	Dlg.m_DiffuseLightColor  = m_DiffuseLightColor;
	
	
	//显示控制参数;
	Dlg.m_bShowGridLine      = m_bShowGridLine;
	Dlg.m_bAmbientLight     = m_bAmbientLight;
	Dlg.m_bDiffuseLight      = m_bDiffuseLight; 
	//}}AFX_DATA

	if ( Dlg.DoModal() == IDOK)
	{
		//================几何模型参数========================
		m_nLongitude_Grids   = Dlg.m_nLongitude_Grids;
		m_nLatitude_Grids    = Dlg.m_nLatitude_Grids;
		m_Ka                 = Dlg.m_Ka;
		m_Kd                 = Dlg.m_Kd;
		
		//网格线参数
		m_nGridLineThickness = Dlg.m_nGridLineThickness;
		m_GridLineColor      = Dlg.m_GridLineColor;
		
		//环境光参数
		m_Ia                 = Dlg.m_Ia;
		m_Id                 = Dlg.m_Id;
		m_AmbientLightColor = Dlg.m_AmbientLightColor;
		
		//漫反射光参数
		m_nShadingMode       = Dlg.m_nShadingMode;
		m_fLen               = Dlg.m_fLen;
		TRACE("before: %f, %f\n", m_fAngleToXOY, m_fAngleOnXOYProjectionToX);

		m_fAngleToXOY        = Dlg.m_fAngleToXOY;
		m_fAngleOnXOYProjectionToX= Dlg.m_fAngleOnXOYProjectionToX;
		m_DiffuseLightColor  = Dlg.m_DiffuseLightColor;
		TRACE("After: %f, %f\n", m_fAngleToXOY, m_fAngleOnXOYProjectionToX );
		
		
		//显示控制参数;
		m_bShowGridLine      = Dlg.m_bShowGridLine;
		m_bAmbientLight     = Dlg.m_bAmbientLight;
		m_bDiffuseLight      = Dlg.m_bDiffuseLight; 

		this->CreateMesh();
		this->Invalidate();
	}
	
}

BOOL CIlluminationView::OnEraseBkgnd(CDC* pDC) 
{
	CView::OnEraseBkgnd(pDC);   
	CRect   rect;   
	GetClientRect(rect);   
	pDC->FillSolidRect(&rect,   RGB(0,0,0));   
    
	return   TRUE;   
	
//	return CView::OnEraseBkgnd(pDC);
}

void CIlluminationView::CreateMesh()
{
	//
	int  origin[3];
	int  radius;

	CRect ClientRect;
	this->GetClientRect(&ClientRect);

	origin[0] = (ClientRect.left + ClientRect.right) / 2;
	origin[1] = (ClientRect.top + ClientRect.bottom) / 2;
	origin[2] = 0; 

	if ( ClientRect.Width() < ClientRect.Height() )
	{
		radius = ClientRect.Width() * 0.5 * 0.8;
	}
	else 
	{
		radius = ClientRect.Height() * 0.5 * 0.8;
	}

	double alfa, belta;
	for ( int i = 0; i < m_nLongitude_Grids; i ++ )
	{
		alfa = (2*PI / m_nLongitude_Grids) * i;
		for ( int j = 0; j < m_nLatitude_Grids; j++)
		{
			//belta = (PI / 3 / (N-1)) * j;
			belta = acos( 1.0 - (0.98 / (m_nLatitude_Grids-1) *j) );

			this->m_Vectices[i][j][0] = 
				origin[0] + ROUND(radius * cos(belta)*cos(alfa));//x coordinate;
			this->m_Vectices[i][j][1] = 
				origin[1] + ROUND(radius * cos(belta)*sin(alfa));//y coordinate;
			this->m_Vectices[i][j][2] = ROUND(radius*sin(belta)); 


			this->m_Normals[i][j][0] = 1.0 * cos(belta) * cos(alfa);  
			this->m_Normals[i][j][1] = 1.0 * cos(belta) * sin(alfa);
			this->m_Normals[i][j][2] = 1.0 * sin(belta);
		}
	}

	//Calculat Light Position
	this->m_Light_Pos[0] = origin[0] + 
		                   radius*m_fLen*(-m_light_direction[0]);  
	this->m_Light_Pos[1] = origin[1] + 
		                   radius*m_fLen*(-m_light_direction[1]);  
	this->m_Light_Pos[2] = origin[2] + 
		                   radius*m_fLen*(-m_light_direction[2]);

	m_light_direction[0] = -cos(m_fAngleToXOY)*cos(m_fAngleOnXOYProjectionToX);
	m_light_direction[1] = -cos(m_fAngleToXOY)*sin(m_fAngleOnXOYProjectionToX);
	m_light_direction[2] = -sin(m_fAngleToXOY);	
}


void CIlluminationView::DrawEdges(CDC * pDC)
{
	if ( !this->m_bShowGridLine)
	{
		return;
	}

	int i, j;
	CPen *pNewPen = new CPen;
	pNewPen->CreatePen(PS_SOLID, this->m_nGridLineThickness, this->m_GridLineColor);
	CPen *pOldPen = pDC->SelectObject(pNewPen);

	//绘制纬线
	for ( j = 0; j < m_nLatitude_Grids; j++)
	{
		for ( i = 0; i < m_nLongitude_Grids; i++ )
		{
			pDC->MoveTo(this->m_Vectices[i][j][0], 
				        this->m_Vectices[i][j][1]);

			pDC->LineTo(this->m_Vectices[(i+1)%m_nLongitude_Grids][j][0], 
				        this->m_Vectices[(i+1)%m_nLongitude_Grids][j][1]);

		}
	}

	//绘制经线

	for ( i = 0; i < m_nLongitude_Grids; i++)
	{
		for ( j =0; j < m_nLatitude_Grids-1; j++)
		{
			pDC->MoveTo(this->m_Vectices[i][j][0],
				        this->m_Vectices[i][j][1]);
			pDC->LineTo(this->m_Vectices[i][j+1][0],
				        this->m_Vectices[i][j+1][1]);
		}
	}

	pDC->SelectObject(pOldPen);

	pNewPen->DeleteObject();
	delete pNewPen;
	
	return;
}


void CIlluminationView::Rendering( CDC *pDC)
{
	int    i, j;
	int    vectices[4][3];
	double normals[4][3];
	for ( i = 0; i < m_nLongitude_Grids; i++ )
	{
		for ( j = 0; j < m_nLatitude_Grids-1; j++ )
		{
			//left bottom
			vectices[0][0] = this->m_Vectices[i][j][0]; 
			vectices[0][1] = this->m_Vectices[i][j][1];
			vectices[0][2] = this->m_Vectices[i][j][2];

			normals[0][0] = this->m_Normals[i][j][0]; 
			normals[0][1] = this->m_Normals[i][j][1];
			normals[0][2] = this->m_Normals[i][j][2];

			//right bottom
			vectices[1][0] = this->m_Vectices[(i+1)%m_nLongitude_Grids][j][0];
			vectices[1][1] = this->m_Vectices[(i+1)%m_nLongitude_Grids][j][1];
			vectices[1][2] = this->m_Vectices[(i+1)%m_nLongitude_Grids][j][2];

			normals[1][0] = this->m_Normals[(i+1)%m_nLongitude_Grids][j][0]; 
			normals[1][1] = this->m_Normals[(i+1)%m_nLongitude_Grids][j][1];
			normals[1][2] = this->m_Normals[(i+1)%m_nLongitude_Grids][j][2];


			//right top
			vectices[2][0] = this->m_Vectices[(i+1)%m_nLongitude_Grids][j+1][0];
			vectices[2][1] = this->m_Vectices[(i+1)%m_nLongitude_Grids][j+1][1];
			vectices[2][2] = this->m_Vectices[(i+1)%m_nLongitude_Grids][j+1][2];

			normals[2][0] = this->m_Normals[(i+1)%m_nLongitude_Grids][j+1][0]; 
			normals[2][1] = this->m_Normals[(i+1)%m_nLongitude_Grids][j+1][1];
			normals[2][2] = this->m_Normals[(i+1)%m_nLongitude_Grids][j+1][2];

			//left top
			vectices[3][0] = this->m_Vectices[i][j+1][0];
			vectices[3][1] = this->m_Vectices[i][j+1][1];
			vectices[3][2] = this->m_Vectices[i][j+1][2];

			normals[3][0] = this->m_Normals[i][j+1][0]; 
			normals[3][1] = this->m_Normals[i][j+1][1];
			normals[3][2] = this->m_Normals[i][j+1][2];


			//--------------------------
			FillQuad(pDC, vectices,normals);
		}
	}
	return;
}



void CIlluminationView::FillQuad(CDC *pDC,
								int Vertices[4][3], 
								double Normals[4][3])
{

	int VertexNum = 4;

	//第一步：获取y的最大值和最小值；
	int i, j;
	int yMin, yMax;
	int x, y, z;
	yMin = yMax = Vertices[0][1];

	for ( i = 1; i < VertexNum; i++ )
	{
		if ( Vertices[i][1] > yMax ) yMax =  Vertices[i][1];
		if ( Vertices[i][1] < yMin ) yMin =  Vertices[i][1];
	}

	//开始处理从Ymin到Ymax的每一条扫描线
	for ( y = yMin; y <= yMax; y++)
	{
		//第一步：求交点
		int IntersectionPoints[2][3];   //最多有两个交点
		int InersectionPointsNum = 0;
		for ( i = 0; i < VertexNum; i++)
		{
			//顶点Vertices[i] 和Vertices[(i+1)%VertexNum]构成直线；
			
			if ( y == Vertices[i][1] ) 
			{
				IntersectionPoints[InersectionPointsNum][0] = Vertices[i][0];
				IntersectionPoints[InersectionPointsNum][1] = Vertices[i][1];
				IntersectionPoints[InersectionPointsNum][2] = Vertices[i][2];

				InersectionPointsNum++;

				if ( (Vertices[i][1] - Vertices[(i-1 + VertexNum ) % VertexNum][1]) *
					 (Vertices[i][1] - Vertices[(i+1 ) % VertexNum][1]) > 0 )   //同一侧
				{
					InersectionPointsNum--; //抛弃这个点；
				}

			}
			else if ( y == Vertices[(i+1)%VertexNum][1] )
			{
				continue;

			}
			else if ( (y - Vertices[(i+1)%VertexNum][1] ) * ( y - Vertices[i][1]) < 0 )
			{
				float u = float (y - Vertices[i][1]) / 
					      float (Vertices[(i+1)%VertexNum][1] - Vertices[i][1]);


				IntersectionPoints[InersectionPointsNum][0] = Vertices[i][0]               * (1-u) + 
					                                          Vertices[(i+1)%VertexNum][0] * u;
				IntersectionPoints[InersectionPointsNum][1] = Vertices[i][1]               * (1-u) + 
					                                          Vertices[(i+1)%VertexNum][1] * u;
				IntersectionPoints[InersectionPointsNum][2] = Vertices[i][2]               * (1-u) + 
					                                          Vertices[(i+1)%VertexNum][2] * u;

				InersectionPointsNum++;

			}
			else  // 没有交点
			{
			}
		} 


		if ( InersectionPointsNum %2 != 0 )
		{
			continue;
			//-------------------
		}
		//第二步交：点排序 bubble sort;

		for ( i = 0; i < InersectionPointsNum -1; i++)
		{
			for ( j = InersectionPointsNum -1; j>i; j--)
			{
				if ( IntersectionPoints[j][0] < IntersectionPoints[j-1][0] ) // swap;
				{
					int tempx, tempy, tempz;
					tempx = IntersectionPoints[j][0];
					tempy = IntersectionPoints[j][1];
					tempz = IntersectionPoints[j][2];

					IntersectionPoints[j][0] = IntersectionPoints[j-1][0];
					IntersectionPoints[j][1] = IntersectionPoints[j-1][1];
					IntersectionPoints[j][2] = IntersectionPoints[j-1][2];

					IntersectionPoints[j-1][0] = tempx;
					IntersectionPoints[j-1][1] = tempy;
					IntersectionPoints[j-1][2] = tempz;
				}
			}
		}
		//第三步：填充

		for ( i = 0; i < InersectionPointsNum; i+=2)
		{

			for ( x = IntersectionPoints[i][0]; x <= IntersectionPoints[i+1][0]; x++)
			{
				float u = float ( x - IntersectionPoints[i][0]) /
					      float (IntersectionPoints[i+1][0] - IntersectionPoints[i][0]);

				z = IntersectionPoints[i][2] * (1 - u) + 
					IntersectionPoints[i+1][2] * u; 

				int Pt[3];
				Pt[0] = x; Pt[1] = y; Pt[2] = z;
				COLORREF PtColor = this->Calculate_Color(Pt,Vertices, Normals);

				DrawPixel(pDC, x, y, PtColor);
			}
		}
	}


	return;
}


void CIlluminationView::OnInitialUpdate() 
{
	CView::OnInitialUpdate();
	
	this->CreateMesh();
	
}


void CIlluminationView::OnSize(UINT nType, int cx, int cy) 
{
	CView::OnSize(nType, cx, cy);
	
	CreateMesh();
	
}

/*********************************************************************
功    能：计算给定点的颜色
参数含义：
          Pt[3]   : 点的坐标，
		  Vertices: 四边形的顶点
		  Normals :四边形顶点的法向量
		  
返 回 值: 点Pt的漫放射光强；

日    期: 2009-10-20
作    者: 罗月童
**********************************************************************/		 
COLORREF CIlluminationView::Calculate_Color(int    Pt[3],
											int    Vertices[4][3],
											double Normals[4][3])
{
	double Ie, Id;

	//Ambient Color;
	int R = 0, G = 0, B = 0;
	if ( this->m_bAmbientLight )
	{
		Ie = this->m_Ia * this->m_Ka;                   //Ambient Light Intensity
		R += Ie * GetRValue(this->m_AmbientLightColor);
		G += Ie * GetGValue(this->m_AmbientLightColor);
		B += Ie * GetBValue(this->m_AmbientLightColor);
	}

	if ( this->m_bDiffuseLight)
	{
		Id = DiffuseLightIntensity(Pt, Vertices,Normals);//Diffuse Light Intensity
		//Diffulse Color
		R += Id * GetRValue(this->m_DiffuseLightColor);
		G += Id * GetGValue(this->m_DiffuseLightColor);
		B += Id * GetBValue(this->m_DiffuseLightColor);
	}

	//To Force R, G, B in rang [0, 255];
	R = ( R <= 255) ? R : 255;
	G = ( G <= 255) ? G : 255;
	B = ( B <= 255) ? B : 255;

	return RGB(R, G, B);
}



/***********************************************************
功    能：计算给定点的光强
参数含义：
         Pt      : 点的坐标
		 Vertices: 四边形的顶点
		 Normals :四边形顶点的法向量

说明：本程序目标不考虑镜面放射光


Date    : 2008-10-20
Author  : Yuetong Luo
************************************************************/
double CIlluminationView::DiffuseLightIntensity(int    Pt[3],
										int    Vertices[4][3],
										double Normals[4][3])
{
	double Id = 0;

	//计算漫放射光的强度；
	switch( this->m_nShadingMode)
	{
	case 0://Const Intensity;
		{
			Id = ConstIntensity(Vertices, 
				               this->m_Light_Pos, 
							   this->m_Id, 
							   this->m_Kd);
			break;
		}
	case 1://Gourand
		{
			Id = Gouraud(Pt, 
				         Vertices, 
						 Normals, 
						 this->m_Light_Pos, 
						 this->m_Id, 
						 this->m_Kd);
			break;
		}
	case 2: //Phong
		{
			Id = Phong(Pt,
				       Vertices, 
					   Normals, 
					   this->m_Light_Pos, 
					   this->m_Id, 
					   this->m_Kd);
			break;
		}
	default:
		{
			break;
		}
	}
	// 

	return Id;
}


/*********************************************************************
功    能：计算给定点的漫放射光强；
参数含义：
          Pt[3]   : 点的坐标，
		  N       : 点的法向量
		  lightPos: 光源的位置
		  Ip      : 漫放射光源的强度
		  Kd      : 漫放射系数

返 回 值: 点Pt的漫放射光强；

日    期: 2009-10-20
作    者: 罗月童
**********************************************************************/
double CIlluminationView::DensityOnPoint( int    Pt[3],
		                                  double N[3],
						                  int    lightPos[3],
						                  double Ip,
						                  double Kd)
{

	double L[3];
	L[0] = lightPos[0] - Pt[0];
	L[1] = lightPos[1] - Pt[1];
	L[2] = lightPos[2] - Pt[2];

	double len = sqrt(L[0]*L[0] + L[1]*L[1] +L[2]*L[2] );
	L[0] /= len;
	L[1] /= len;
	L[2] /= len;


	double dist = (L[0]-N[0])*(L[0]-N[0]) + 
		          (L[1]-N[1])*(L[1]-N[1]) + 
				  (L[2]-N[2])*(L[2]-N[2]);

	dist = sqrt(dist);

	double theta = 2.0 * asin(dist/2.0);

	double Id = Ip * Kd * cos(theta);

	Id = (Id < 0) ? 0 : Id;
	return Id;

	return Id;
}
/*
double CIlluminationView::DensityOnPoint(int Pt[3],  
                                           double N[3],  
                                           int lightPos[3],  
                                           double Ip,  
                                           double Kd)  
{  
    // 将光源位置转换为双精度浮点数  
    double lightPosDouble[3];  
    lightPosDouble[0] = static_cast<double>(lightPos[0]);  
    lightPosDouble[1] = static_cast<double>(lightPos[1]);  
    lightPosDouble[2] = static_cast<double>(lightPos[2]);  
  
    // 计算从光源到点的向量  
    double L[3];  
    L[0] = lightPosDouble[0] - Pt[0];  
    L[1] = lightPosDouble[1] - Pt[1];  
    L[2] = lightPosDouble[2] - Pt[2];  
  
    // 单位化向量L  
    double lenL = sqrt(L[0]*L[0] + L[1]*L[1] + L[2]*L[2]);  
    if (lenL > 0.0) {  
        L[0] /= lenL;  
        L[1] /= lenL;  
        L[2] /= lenL;  
    } else {  
        // 如果L是零向量，则返回0光强  
        return 0.0;  
    }  
  
    // 假设N已经是单位向量，直接计算点积  
    double dotProduct = N[0]*L[0] + N[1]*L[1] + N[2]*L[2];  
  
    // 点积结果即为两个单位向量之间夹角的余弦值  
    double cosTheta = dotProduct;  
  
    // 确保余弦值在[-1, 1]范围内（虽然理论上点积结果应该已经在这个范围内）  
    cosTheta = std::max(-1.0, std::min(1.0, cosTheta));  
  
    // 计算漫反射光强  
    double Id = Ip * Kd * std::max(0.0, cosTheta); // 确保光强非负  
  
    return Id;  
}

*/



/*********************************************************************
功    能：实现Gouraud光照模型，求给定点的漫放射光强；
参数含义：
		  Vertices:点Pt所在的四边形的四个顶点坐标，顶点按逆时针顺序存放
		  lightPos:光源的位置
		  Ip      :漫放射光源的强度
		  Kd      :漫放射系数
返 回 值: 点Pt的漫放射光强；

日    期: 2009-10-20
作    者: 罗月童
**********************************************************************/
double CIlluminationView::ConstIntensity( int      Vertices[4][3], 
						                 int      lightPos[3],
						                 double   Ip,
						                 double   Kd)
{
	//计算面片的法向量
	double v1[3], v2[3], N[3];
	v1[0] = Vertices[1][0] -  Vertices[0][0]; 
	v1[1] = Vertices[1][1] -  Vertices[0][1]; 
	v1[2] = Vertices[1][2] -  Vertices[0][2]; 

	v2[0] = Vertices[2][0] -  Vertices[1][0]; 
	v2[1] = Vertices[2][1] -  Vertices[1][1]; 
	v2[2] = Vertices[2][2] -  Vertices[1][2]; 

	N[0] = v1[1]*v2[2] - v1[2]*v2[1];
	N[1] = v1[2]*v2[0] - v1[0]*v2[2];
	N[2] = v1[0]*v2[1] - v1[1]*v2[0];

	//normalize;
	double len = sqrt(N[0]*N[0] + 
		              N[1]*N[1] + 
					  N[2]*N[2]); 

	N[0] /= len;
	N[1] /= len;
	N[2] /= len;
	if ( N[2]  < 0 )
	{
		N[0] = -N[0];
		N[1] = -N[1];
		N[2] = -N[2];
	}

	//计算光强；
	int    Pt[3];
	Pt[0] = (Vertices[0][0] + Vertices[1][0] + Vertices[2][0] +Vertices[3][0]) /4;
	Pt[1] = (Vertices[0][1] + Vertices[1][1] + Vertices[2][1] +Vertices[3][1]) /4;
	Pt[2] = (Vertices[0][2] + Vertices[1][2] + Vertices[2][2] +Vertices[3][2]) /4;

	double Id = DensityOnPoint(Pt, N, lightPos, Ip, Kd);

	return Id;
}


/*********************************************************************
功    能：实现Gouraud光照模型，求给定点的漫放射光强；
参数含义：
          Pt[3]   : 点的坐标，
		  Vertices:点Pt所在的四边形的四个顶点坐标，顶点按逆时针顺序存放
		  Normals :四个顶点的法向量，和Vertices按同样的顺序存放；
		  lightPos:光源的位置
		  Ip      :漫放射光源的强度
		  Kd      :漫放射系数
返 回 值: 点Pt的漫放射光强；

可调用函数：
     ===========================================================================
     bool   BilinearInterpolation(int Vertices[4][3], int P[3], double uv[4]);
	 功    能：在凸四边形中实现二次线性插值

     参数含义：
          Vertices    : 四边形的四个顶点，按逆时针存储；
		  P           : 点的坐标
		  uv          : 用于返回插值系数；

     返 回 值: True-插值成功，Flase-插值失败（如果Pt不在四边形内部，则失败）
	 ===========================================================================

日    期: 2009-10-20
作    者: 罗月童
**********************************************************************/
double CIlluminationView::Gouraud (int    Pt[3],
		                           int    Vertices[4][3],
		                           double Normals[4][3],
					               int    lightPos[3],
				 	               double Ip,
					               double Kd)
{

	double I = 0;   //itensity;
	double ins[4] = {0.0};
	double uv[4] = {0.0};
	
	// 插值运算求出插值系数uv
	if(!BilinearInterpolation(Vertices, Pt, uv))
	{
		return I;
	}
	// 对每个顶点的漫反射光强度进行计算
	for(int i = 0; i < 4; i++)
	{
		ins[i] = DensityOnPoint(Vertices[i],Normals[i],lightPos,Ip,Kd);
		I += uv[i] * ins[i];
	}

	//给定点到光源的距离
	double dx = Pt[0] - lightPos[0];
	double dy = Pt[1] - lightPos[1];
	double dz = Pt[2] - lightPos[2];
	double dis = sqrt(dx * dx + dy * dy + dz * dz);
	double D = 1000 / 1 + dis + dis * dis;
	if(D > 1){
		D = 1;
	}
	I = I * ((1700 * 1700 / ((dis * dis) + 1700)));
	//I = I * dis * 0.001;
	return I;

}

/*double CIlluminationView::Gouraud(int Pt[3],  
                                   int Vertices[4][3],  
                                   double Normals[4][3],  
                                   int lightPos[3],  
                                   double Ip,  
                                   double Kd)  
{  
    double I = 0;   // 初始化漫反射光强为0  
    double uv[4];   // 用于存储双线性插值的系数  
  
    // 调用双线性插值函数判断点Pt是否在四边形内部，并计算插值系数  
    if (!BilinearInterpolation(Vertices, Pt, uv)) {  
        return -1; // 如果插值失败（即点不在四边形内部），则返回-1  
    }  
  
    double intensityAtVertices[4]; // 存储四个顶点处的光照强度  
  
    // 遍历四边形的每个顶点  
    for (int i = 0; i < 4; ++i) {  
        // 计算光源到当前顶点的方向向量  
        double lightDir[3] = {  
            lightPos[0] - Vertices[i][0],  
            lightPos[1] - Vertices[i][1],  
            lightPos[2] - Vertices[i][2]  
        };  
  
        // 计算方向向量的模（长度）  
        double norm = sqrt(lightDir[0] * lightDir[0] +  
                           lightDir[1] * lightDir[1] +  
                           lightDir[2] * lightDir[2]);  
  
        // 归一化方向向量  
        lightDir[0] /= norm;  
        lightDir[1] /= norm;  
        lightDir[2] /= norm;  
  
        // 计算方向向量与顶点法向量的点积  
        double dot = lightDir[0] * Normals[i][0] +  
                     lightDir[1] * Normals[i][1] +  
                     lightDir[2] * Normals[i][2];  
  
        // 确保点积非负  
        dot = max(dot, 0.0);  
  
        // 计算顶点处的光照强度  
        intensityAtVertices[i] = Ip * Kd * dot;  
    }  
  
    // 使用双线性插值系数和顶点光照强度计算点Pt处的光照强度  
    for (int j = 0; j < 4; ++j) {  
        I += intensityAtVertices[j] * uv[j];  
    }  
  
    // 返回点Pt处的漫反射光强  
    return I;  
}*/




// 求cosTheta的分子
template <typename T1, typename T2>
T1 up(T1 a[], T2 b[], size_t N)
{
	T1 res = 0.0;
    for(size_t i = 0; i < N; i++)
    {
        res += a[i] * b[i];
    }
    return res;
}

// 求解cosTheta分母
template <typename T>
T down(T a[], size_t N)
{
    T res = 0.0;
    for (size_t i = 0; i < N; i++)
    {
        res += a[i] * a[i];
    }
    return sqrt(res);
}
/*********************************************************************
功    能：实现Phong光照模型，求给定点的漫放射光强；
参数含义：
          Pt[3]   : 点的坐标，
		  Vertices:点Pt所在的四边形的四个顶点坐标，顶点按逆时针顺序存放
		  Normals :四个顶点的法向量，和Vertices按同样的顺序存放；
		  lightPos:光源的位置
		  Ip      :漫放射光源的强度
		  Kd      :漫放射系数
返 回 值: 点Pt的漫放射光强；

可调用函数：
     ===========================================================================
     bool   BilinearInterpolation(int Vertices[4][3], int P[3], double uv[4]);
	 功    能：在凸四边形中实现二次线性插值

     参数含义：
          Vertices    : 四边形的四个顶点，按逆时针存储；
		  P           : 点的坐标
		  uv          : 用于返回插值系数；

     返 回 值: True-插值成功，Flase-插值失败（如果Pt不在四边形内部，则失败）
	 ===========================================================================


日    期: 2009-10-20
作    者: 罗月童
**********************************************************************/
double CIlluminationView::Phong(int    Pt[3],
								int    Vertices[4][3],
								double Normals[4][3],
								int    lightPos[3],
								double Ip,
								double Kd)
{
	double I = 0;   //itensity;
	double uv[4] = {0.0};
	
	// 插值运算求出插值系数uv
	if(!BilinearInterpolation(Vertices, Pt, uv))
	{
		return I;
	}

	// 求出插值的法向量
	double pt_normal[3] = {0.0};
	for (int i = 0; i < 3; i++)
    {
        for (int j = 0; j < 4; j++)
        {
            pt_normal[i] += Normals[j][i] * uv[j];
        }
    }
	
	// 计算点Pt到光源的方向向量和向量长度
    double lightDir[3] = {0};
    for (int j = 0; j < 3; j++)
    {
        lightDir[j] = lightPos[j] - Pt[j];
    }

	// 计算漫反射强度
	double cosUp = up(pt_normal, lightDir, 3);
	double cosDown = down(pt_normal, 3) * down(lightDir, 3);
	double cosTheta = cosUp / cosDown;
	if(cosTheta < 0.0) 
			cosTheta = 0.0;
	I = Ip * Kd * cosTheta;

	//给定点到光源的距离
	double dx = Pt[0] - lightPos[0];
	double dy = Pt[1] - lightPos[1];
	double dz = Pt[2] - lightPos[2];
	double dis = sqrt(dx * dx + dy * dy + dz * dz);
	double D = 1000 / 1 + dis + dis * dis;
	if(D > 1){
		D = 1;
	}
	//I = I * ((1700 * 1700 / ((dis * dis) + 1700)));
	//I = I * dis * 0.001;

    // 自定义视线的位置  
	double eysPosition[3] = {0.0};  
	for(int m = 0; m < 3; m++)  
	{  
		eysPosition[m] = lightPos[m] + (m*100 + 50);  
	}  

	// 计算点Pt到观察者的方向向量, 即V向量  
	double viewDir[3] = {0};  
	for (int n = 0; n < 3; n++)  
	{  
		viewDir[n] = eysPosition[n] - Pt[n];  
	}  
	 
	// 求反射向量R  
	double refDir[3] = {0.0};  
	double temp_up = up(lightPos,pt_normal, 3);  
	for(int p = 0; p < 3; p++)  
	{  
		refDir[p] = 2 * (temp_up * pt_normal[p]) - lightPos[p];  
	   
	}  
	
	// 定义镜面反射光属性  
	double ks = 0.8;  
	double m_Shininess = 5; 
	double Ie = 1;
	
	// 求解镜面反射光  
	double cosAlpha = up(viewDir, refDir, 3) / (down(viewDir, 3) * down(refDir, 3));  
	if(cosAlpha < 0)  
		cosAlpha = 0.0;  

	double specular = Ie * ks * pow(cosAlpha, m_Shininess);  
	 
	I += specular;


	return I;

}
/*double CIlluminationView::Phong(int Pt[3],                      // 给定点的坐标  
                               int Vertices[4][3],                // 四边形的四个顶点坐标  
                               double Normals[4][3],            // 四个顶点的法向量  
                               int lightPos[3],                   // 光源的位置  
                               double Ip,                         // 漫反射光源的强度  
                               double Kd)                         // 漫反射系数  
{  
    double I = 0; // 初始化漫反射光强为0  
    double uv[4]; // 用于存储双线性插值得到的系数  
  
    // 调用双线性插值函数，如果失败则返回-1  
    if (!BilinearInterpolation(Vertices, Pt, uv)) {  
        return -1;  
    }  
  
    // 初始化插值法向量为0向量  
    double normalAtPt[3] = {0, 0, 0};  
  
    // 根据双线性插值得到的系数和四个顶点的法向量，计算点Pt处的插值法向量  
    for (int i = 0; i < 4; ++i) {  
        normalAtPt[0] += Normals[i][0] * uv[i];  
        normalAtPt[1] += Normals[i][1] * uv[i];  
        normalAtPt[2] += Normals[i][2] * uv[i];  
    }  
  
    // 归一化插值法向量  
    double norm = sqrt(normalAtPt[0] * normalAtPt[0] +  
                       normalAtPt[1] * normalAtPt[1] +  
                       normalAtPt[2] * normalAtPt[2]);  
    if (norm != 0) { // 避免除以0的情况  
        normalAtPt[0] /= norm;  
        normalAtPt[1] /= norm;  
        normalAtPt[2] /= norm;  
    }  
  
    // 计算光源到点Pt的方向向量  
    double lightDir[3] = {  
        lightPos[0] - Pt[0],  
        lightPos[1] - Pt[1],  
        lightPos[2] - Pt[2]  
    };  
  
    // 归一化光源方向向量  
    norm = sqrt(lightDir[0] * lightDir[0] +  
                lightDir[1] * lightDir[1] +  
                lightDir[2] * lightDir[2]);  
    if (norm != 0) { // 避免除以0的情况  
        lightDir[0] /= norm;  
        lightDir[1] /= norm;  
        lightDir[2] /= norm;  
    }  
  
    // 计算光源方向与法向量的点积（即夹角余弦值）  
    double dot = lightDir[0] * normalAtPt[0] +  
                 lightDir[1] * normalAtPt[1] +  
                 lightDir[2] * normalAtPt[2];  
  
    // 如果点积大于0（即光源方向与法向量之间的夹角小于90度），则计算漫反射光强  
    if (dot > 0) {  
        I = Ip * Kd * dot;  
    }  
  
    // 返回漫反射光强  
    return I;  
}*/
