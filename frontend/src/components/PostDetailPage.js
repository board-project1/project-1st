import React, {useEffect, useState} from 'react';
import {Card, CardContent, TextField, Typography} from '@mui/material';
import {red} from '@mui/material/colors';
import {blue, CustomButton} from './CustomButton';
import {StyledTextarea} from './StyledTextArea';
import {useNavigate} from 'react-router-dom';

const PostDetailPage = () => {
  const navigate = useNavigate();
  const [post, setPost] = useState({});
  const [comments, setComments] = useState([]);

  const [newComment, setNewCommnent] = useState({
    content: '',
    author: '',
  });

  async function fetchData() {
    await fetch('http://localhost:8080/api/comments')
      .then((res) => res.json())
      .then((res) => {
        if (!res) return;
        setComments(res.filter((c) => c?.post_id === post.id));
      });
  }

  useEffect(() => {
    const postData = JSON.parse(localStorage.getItem('post'));
    setPost({ ...postData });
    try {
      fetchData();
    } catch (e) {
      console.error(e);
    }
  }, []);

  const handlePostChange = async () => {
    const token = localStorage.getItem('Authorization');
    await fetch(`http://localhost:8080/api/posts/${post.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, // 토큰을 헤더에 포함
      },
      body: JSON.stringify({
        title: post?.title || '',
        content: post?.content || '',
      }),
    })
      .then(() => navigate('/'))
      .catch((err) => console.error(err));
  };

  const handleCommentChange = async (id, content) => {
    const token = localStorage.getItem('Authorization');
    await fetch(`http://localhost:8080/api/comments/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, // 토큰을 헤더에 포함
      },
      body: JSON.stringify({
        content: content,
      }),
    })
    .then(() => {
      window.location.reload(); // 페이지 데이터 리로드
    })
    .catch((err) => console.error(err));
  };

  const changeComment = (commentId, comment) => {
    const indexToUpdate = comments.findIndex((item) => item.id === commentId);
    const newComments = comments;
    if (indexToUpdate !== -1) {
      newComments[indexToUpdate] = {
        ...newComments[indexToUpdate],
        content: comment,
      };
      setComments([...newComments]);
    }
  };

  const submitComment = async () => {
    const token = localStorage.getItem('Authorization');

    await fetch(`http://localhost:8080/api/comments`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, // 토큰을 헤더에 포함
      },
      body: JSON.stringify({
        content: newComment.content,
        nickname: newComment.author,
        postId: post.id,
      }),
    })
      .then(() => {
        setNewCommnent({ content: '', author: '' }); // 댓글 작성 후 입력란 초기화
        fetchData(); // 댓글을 다시 가져옵니다.
      })
      .then(() => {
        window.location.reload(); // 페이지 데이터 리로드
      })
      .catch((err) => console.error(err));
  };

  const handleCommentDelete = async (id) => {
    const token = localStorage.getItem('Authorization');
    const postId = post.id;

    await fetch(`http://localhost:8080/api/comments/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`, // 토큰을 헤더에 포함
      },
      body: JSON.stringify({
        postId: postId,
      }),
    })
      .then(() => fetchData()) // 댓글을 다시 가져옵니다.
      .then(() => {
        window.location.reload(); // 페이지 데이터 리로드
      })
      .catch((err) => console.error(err));
  };

  return (
    <div style={{
    padding: '40px'
    }}>
    <h1>게시판 상세</h1>
    <h2>글 제목</h2>
    <TextField id="outlined-basic" label="Outlined" variant="outlined" value={post?.title || ''}
    onChange={(event) => setPost(prev => ({
    ...prev,
    title: event.target.value
    }))}/>
    <h2>작성자</h2>
    <TextField id="outlined-basic" label="Outlined" variant="outlined" value={post?.author?.email || ''}
    onChange={(event) => setPost(prev => ({
    ...prev,
    author: event.target.value
    }))}/>
    <h2>본문</h2>
    <StyledTextarea
    aria-label="minimum height"
    minRows={3}
    placeholder="Minimum 3 rows"
    value={post?.content || ''}
    onChange={(event) => setPost(prev => ({
    ...prev,
    content: event.target.value
    }))}
    />
    <div style={{
    marginTop: '20px'
    }}>
    <CustomButton style={{ backgroundColor: blue[500] }} onClick={handlePostChange}>수정</CustomButton>
    <CustomButton style={{ backgroundColor: red[500] }} onClick={() => navigate('/')}>취소</CustomButton>
    </div>
    <div style={{marginTop: 20}}>
    <Card sx={{ marginBottom: 2 }}>
    <CardContent style={{display: 'flex', flexDirection: 'column'}}>
    <h3>댓글 작성자</h3>
    <TextField variant="outlined" value={newComment.author || ''}
    onChange={(event) => setNewCommnent(prev => ({...prev, author: event.target.value}))}/>
    <h3>댓글 내용</h3>
    <TextField variant="outlined" value={newComment.content || ''}
    onChange={(event) => setNewCommnent(prev => ({...prev, content: event.target.value}))}/>
    <CustomButton style={{ backgroundColor: blue[500], marginTop: 10 }} onClick={submitComment}>생성</CustomButton>
    </CardContent>
    </Card>
    {comments.length > 0 && (
    comments.map((c, index) => (
    <Card sx={{ marginBottom: 2 }}>
    <CardContent>
    <TextField variant="outlined" value={c?.content || ''}
    onChange={(event) => changeComment(c.id, event.target.value)}/>
    <Typography variant="h5" component="div">
    {c?.author?.email || ''}
    </Typography>
    <Typography color="text.secondary">
    {c?.created_at || ''}
    </Typography>
    <CustomButton style={{ backgroundColor: blue[500] }} onClick={() => handleCommentChange(c.id, c.content)}>수정</CustomButton>
    <CustomButton style={{ backgroundColor: red[500] }} onClick={() => handleCommentDelete(c.id)}>삭제</CustomButton>
    </CardContent>
    </Card>
    )))
    }
    </div>
    </div>
    );
};

export default PostDetailPage;
