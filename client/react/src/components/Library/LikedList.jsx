/*
작성자: 이지희
날짜(수정포함): 2023-09-11
설명: 라이브러리 페이지 내 좋아요 누른 곡 5개 리스트 
*/

import { useEffect, useState } from "react";
import SingleMusic from './SingleMusic.jsx';
import axios from 'axios';


const LikedList = () => {
// 임의의 사용자 아이디
let userId = 1;

const [likedMusics, setLikedMusics] = useState([]);

useEffect(() => {
  const fetchLikedList = async () => {
    try { 
      const response = await axios.get(`/likemusics/${userId}`);
      setLikedMusics(response.data);
      console.log('likedMusics',likedMusics);
    } catch (error) {
      console.error(`Error: ${error}`);
    }
  };

  fetchLikedList();
}, []);

// console.log('musics', likedMusics);
// console.log('id', likedMusics[0].id);

    return (
        <div>
            {likedMusics.slice(0, 5).map((song) => (
                <SingleMusic key={song.id} item={song} />
            ))}
        </div>
    );
};

export default LikedList;
