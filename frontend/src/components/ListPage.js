import React, { useEffect, useState } from "react";
import { blue, CustomButton } from "./CustomButton";
import { useNavigate } from "react-router-dom";
import { red } from "@mui/material/colors";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  TextField,
} from "@mui/material";
import moment from "moment";
import "moment-timezone";
import IconButton from "@mui/material/IconButton";
import FavoriteIcon from "@mui/icons-material/Favorite";
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';

const ListPage = () => {
  const navigate = useNavigate();
  const [keyword, setKeyword] = useState("");
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    async function fetchData() {
      const token = localStorage.getItem("Authorization");
      if (!token) {
        navigate("/login");
        return;
      }
  
      try {
        const headers = {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        };
        const res = await fetch("http://localhost:8080/api/posts", { headers });
        const data = await res.json();
        setPosts([...data]);
        console.log(data);
      } catch (error) {
        console.error(error);
      }
    }
  
    fetchData();
  }, []);

  const logoutHandler = async () => {
    const token = localStorage.getItem("Authorization");

    if (!token) {
      navigate("/login");
      return;
    }
    await fetch(`http://localhost:8080/api/logout`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`, // 토큰을 헤더에 포함
      },
    })
      .then((res) => res.json())
      .then(() => {
        navigate("/login");
        localStorage.removeItem("Authorization");
      })
      .catch((error) => console.error(error));
  };

  const searchHandler = async () => {
    if (!/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/.test(keyword)) return;
    try {
      const res = await fetch(
        `http://localhost:8080/api/posts/search?email=${keyword}`
      );
      const data = await res.json();

      if (Array.isArray(data)) {
        setPosts([...data]);
      } else {
        // 결과가 배열이 아니거나, 예상하지 못한 데이터 형식이면 일반 오류 처리.
        throw new Error("Invalid data format");
      }
    } catch (error) {
      console.error(error);
      alert("게시물을 검색하는 도중 오류가 발생했습니다.");
    }
  };

  const formatDate = (dateString) => {
    if (!dateString) {
      return ""; // 또는 '0000-00-00 00:00:00' 같은 기본 값으로 대체하세요
    }

    // 원하는 시간대를 설정하세요.
    const timezone = "Asia/Seoul";

    // 반환된 시간을 moment 객체로 변환하고, 설정한 시간대로 변환합니다.
    const date = moment(dateString).tz(timezone);

    // 원하는 포맷 (예: YYYY-MM-DD HH:mm:ss)으로 포맷팅하고 리턴합니다.
    return date.format("YYYY-MM-DD HH:mm:ss");
  };

  const handlePostDelete = async (event, postId) => {
    event.stopPropagation(); // 테이블 로우 클릭 이벤트 최상단의 `/post/:id`로 이동하는 것을 막기 위해

    const token = localStorage.getItem("Authorization");

    await fetch(`http://localhost:8080/api/${postId}/delete`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`, // 토큰을 헤더에 포함
      },
    })
      .then(() => {
        window.location.reload(); // 페이지 데이터 리로드
      })
      .catch((err) => console.error(err));
  };

  const handleLikePost = async (event, postId) => {
    event.stopPropagation();
  
    const token = localStorage.getItem('Authorization');
  
    try {
      await fetch(`http://localhost:8080/api/posts/like/${postId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });
  
      // 좋아요 상태를 업데이트하고 클라이언트에서도 상태를 반영합니다.
      setPosts(
        posts.map((post) =>
          post.id === postId ? { ...post, isLiked: !post.isLiked } : post
        )
      );
      window.location.reload();
    } 
    catch (error) {
      console.error(error);
    }
  };


  return (
    <div
      style={{
        padding: "40px",
      }}
    >
      <h1>게시판 리스트</h1>
      <div>
        <TextField
          id="standard-required"
          label="작성자 이메일 검색"
          variant="standard"
          value={keyword}
          onChange={(event) => setKeyword(event.target.value)}
        />
        <CustomButton
          style={{ backgroundColor: blue[500] }}
          onClick={searchHandler}
        >
          검색
        </CustomButton>
      </div>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            <TableCell>이름</TableCell>
            <TableCell>게시물내용</TableCell>
            <TableCell>작성자</TableCell>
            <TableCell>작성일시</TableCell>
            <TableCell>Like</TableCell>
            <TableCell></TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {posts.map((post) => (
            <TableRow
              key={post.id}
              sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
              onClick={() => {
                localStorage.setItem("post", JSON.stringify({ ...post }));
                navigate(`/post/${post.id}`);
              }}
            >
              <TableCell component="th" scope="row">
                {post.title}
              </TableCell>
              <TableCell>{post.content}</TableCell>
              <TableCell>{post.author}</TableCell>
              <TableCell>{formatDate(post.createAt)}</TableCell>
              <TableCell>
                <IconButton
                  style={{ color: red[500] }}
                  onClick={(event) => handleLikePost(event, post.id)}
                >
                  {post.isLiked ? <FavoriteIcon /> : <FavoriteBorderIcon />}
                </IconButton>
                {post.likeCount}
              </TableCell>
              <TableCell>
                <CustomButton
                  style={{ backgroundColor: red[500] }}
                  onClick={(event) => handlePostDelete(event, post.id)}
                >
                  삭제
                </CustomButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
      <CustomButton
        style={{ backgroundColor: blue[500] }}
        onClick={() => navigate("/post/create")}
      >
        게시글 작성
      </CustomButton>
      <CustomButton
        style={{ backgroundColor: red[500] }}
        onClick={logoutHandler}
      >
        로그아웃
      </CustomButton>
    </div>
  );
};

export default ListPage;
