const express = require('express');
const multer = require('multer');
const app = express();
const PORT = 5500;

// 이미지 저장을 위한 multer 설정
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, 'uploads/')  // 파일이 저장될 경로
  },
  filename: function (req, file, cb) {
    cb(null, file.fieldname + '-' + Date.now() + '.' + file.originalname.split('.').pop())
  }
});

const upload = multer({ storage: storage });

app.use(express.static('public'));  // 'public' 폴더를 정적 파일 제공 폴더로 설정

// 이미지 업로드 라우트 설정
app.post('/upload/1', upload.single('image'), (req, res) => {
  if (!req.file) {
    return res.send('파일이 전송되지 않았습니다.');
  }
  res.send(`파일이 업로드 되었습니다: ${req.file.path}`);
});

app.post('/upload/2', upload.single('image'), (req, res) => {
  if (!req.file) {
    return res.send('파일이 전송되지 않았습니다.');
  }
  res.send(`파일이 업로드 되었습니다: ${req.file.path}`);
});


app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
