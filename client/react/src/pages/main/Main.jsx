/*
작성자: 신지훈
날짜: 2023-09-11
설명: 최근 재생목록 불러오기
*/
/*
작성자: 이지희
날짜(수정포함): 2023-09-18
설명: musicIds 설정 관련 수정
*/
/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 나의 추천 플레이리스트, 남의 추천 플레이리스트 연결 및 화면 구현
*/
import React, { useEffect, useState } from "react";
import SavedPlaylist from "../../components/Library/SavedPlaylist";
import BeforeRegistration from "./BeforeRegistration";
import LikedList from "../../components/Library/LikedList";
import { Swiper, SwiperSlide } from "swiper/react";
import MusicDummy from "../../dummy/MusicDummy.json";
import SingleMusic from "../../components/Library/SingleMusic";
import { Link } from "react-router-dom";
import RecPlayList from "../rec/RecPlayList";
import axios from "axios";
// 리덕스 - 지희 경로변경
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds,setPlayingStatus } from "../../store/music/musicActions.js";
// 미니플레이어 import
//유영 추천 음악 플레이 리스트
import SingleRecPlayList from "../rec/SingleRecPlayList";

const Main = () => {
  const [recentlyPlayed, setRecentlyPlayed] = useState([]);
  const [myRecMusicList, setMyRecMusicList] = useState([]);
  const [otherRecMusicList, setOtherRecMusicList] = useState([])

  // 지희 시작 (0918)
  const playingStatus = useSelector(state => state.musicReducer.playingStatus);
  const [ids, setIds] = useState([]);

  // const [id, setId] = useState('0');
  // const id = localStorage.getItem('id')
  const dispatch = useDispatch();
  const musicIds = useSelector(state => state.musicReducer.musicIds);
  const user = useSelector(state => state.memberReducer.user)
  const id = user.id
  const isLoggendIn = user.isLogin
  console.log('header',user);



  useEffect(() => {
    const fetchRecentlyPlayed = async () => {
      try {
        const userId = 1; // 임의의 사용자 아이디
        const response = await axios.get(`/playedmusic/get/${userId}`);
        setRecentlyPlayed(response.data);
        // 지희(0918) - MusicIds 설정 추가
        setIds(response.data.map(music => music.musicId))
        //
        .get(`http://172.30.1.27:8104/recMusiclist/recent10`)//남의 추천 플리 불러오기
        .then((res) => {
          setOtherRecMusicList(res.data)//남의 플레이 리스트
          console.log(res);
        })
        .catch((err) => console.log(err))
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };

    fetchRecentlyPlayed();
  }, []);

  //id가 바뀔 때 나의 추천 플리 불러오기 => 초기값 0이라 처음에 res.data가 null로 되기 때문
  useEffect(() => {
        axios.get(`http://172.30.1.27:8104/recMusiclist/recent/${id}`)//나의 추천 플리 불러오기
        .then((res) => {
          setMyRecMusicList(res.data)//나의 플레이리스트에 저장
          console.log('myplaylist',res);
        })
        .catch((err) => console.log(err))
  }, [id])
 
  // 지희(0918) - MusicIds 설정
 const handleListClick = () => {
    dispatch(setMusicIds(ids));
  };

  return (
    <div className="overflow-auto mb-16 bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full pb-20 hide-scrollbar">
      <br />
      <br />
      {/* ChatGPT가 추천해준 나의 플레이리스트 */}
      <div className="py-2 flex justify-between">
        <h1 className="overflow-hidden text-left indent-1 text-xl font-semibold tracking-tighter mt-5 ">
          Chat GPT가 추천해준 나의 플레이리스트
        </h1>
        {myRecMusicList !== '' &&
          <Link to='/RecPlayList'>
            <button className="flex text-custom-lightgray mt-6 mr-2 text-sm">더보기</button>
          </Link>
        }
        
      </div>
       {id !== '0'  && myRecMusicList.length !== 0 ?
         <Swiper direction={"vertical"} slidesPerView={4} className="h-[33%]">
         {myRecMusicList && myRecMusicList.recMusiclistDetails.map((item) => (
                    <SwiperSlide key={item.recMusiclistDetailId}>
                        <SingleRecPlayList key={item.musicId} item={item} />
                    </SwiperSlide>
                ))}
       </Swiper> :    <BeforeRegistration />}
      

      {/* ChatGPT가 추천해준 남의 플레이리스트 */}
      <div>
        <h1 className="text-left indent-1 text-xl font-semibold tracking-tighter mt-8 mb-5">
          ChatGpt가 추천해준 남의 플레이리스트
        </h1>
        <Swiper spaceBetween={5} slidesPerView={2.1}>
         {otherRecMusicList && otherRecMusicList.map((item) => (
                    <SwiperSlide key={item.recMusiclistId} className="mr-10">
                      <div className="flex flex-col justify-center items-center ml-2 mr-5 w-50 "></div>
                      <img src={"data:image/;base64,"+item.img} alt="album cover" className="w-[170px] h-[160px] rounded-lg mr-5 mb-3" />
                      <span className="tracking-tight text-sm text-center">{item.recMusiclistTitle}</span>
                        {/* <SavedPlaylist key={item.recMusiclistId} item={item} /> */}
                    </SwiperSlide>
                ))}
        </Swiper>
        {/* <Link to="RecPlayList">
          <SavedPlaylist />
        </Link> */}
      </div>

      {/* 최근에 재생한 목록 */}
      <div onClick={handleListClick}>
      <h1 className="text-left indent-1 text-xl font-semibold tracking-tighter mt-8 mb-2">최근에 재생한 목록</h1>
      {/* <Swiper direction={"vertical"} slidesPerView={2} className="h-[16%]"> */}
      <div onClick={setMusicIds}>
      {recentlyPlayed.map((song) => (
        <SingleMusic key={song.id} item={song} />
      ))}
      </div>
      </div>
    </div>
  );
};

export default Main;
