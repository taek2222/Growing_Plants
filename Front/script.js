// 기상청 API 엔드포인트
const apiUrl = 'http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst';

// 서비스키 입력
const serviceKey = 'UywwlFo1/AJ4cWclZif5LrQEPu41/ADGWn3OOnPPAf0v5E+krt8OZiOpUU2RaJaLCSBqa9D6tBe3WFSICiI66g==';

// 필요한 파라메터 설정
const params = {
  serviceKey: serviceKey,
  numOfRows: 1000,
  pageNo: 1,
  base_date: '20230325', // 예: 조회 날짜 (YYYYMMDD)
  base_time: '0600', // 예: 조회 시간 (0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300)
  nx: 60, // 예: 전국 X 좌표계의 X 좌표값
  ny: 127, // 예: 전국 Y 좌표계의 Y 좌표값
  dataType: 'XML'
};

