/*
작성자: 이지희
날짜(수정포함): 2023-09-18
설명: MusicIds 수정
*/


import { useEffect, useState } from "react";
import SingleMusic from './SingleMusic.jsx';
import axios from 'axios';
// 리덕스
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds } from "../../store/music/musicActions.js";

const LikedList = () => {
// 사용자 user_id
  const user = useSelector(state => state.memberReducer.user)
  const userId = user.id
  
const [ids, setIds] = useState([]);

const dispatch = useDispatch();

  const [likedMusics, setLikedMusics] = useState([]);

useEffect(() => {
  const fetchLikedList = async () => {
    try { 
      const response = await axios.get(`/likemusics/${userId}`);
      setLikedMusics(response.data);
      setIds(response.data.map(music => music.musicId))
      console.log('ids', ids);
      
    } catch (error) {
      console.error(`Error: ${error}`);
    }
  };

  fetchLikedList();
}, []);

const handleListClick = () => {
  dispatch(setMusicIds(ids));
};

    return (
        <div onClick={handleListClick}>
            {likedMusics.slice(0, 5).map((song) => (
                <SingleMusic key={song.musicId} item={song} />
            ))}
        </div>
    );
};

export default LikedList;
