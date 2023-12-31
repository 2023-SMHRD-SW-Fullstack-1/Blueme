/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 추천 음악 플레이 리스트
*/

/*
작성자: 신지훈
날짜(수정포함): 2023-09-22
설명: 모바일 크기 조정
*/
import React, { useEffect, useState } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import SingleRecPlayList from "../../components/recommend/SingleRecPlayList";
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds } from "../../store/music/musicActions.js";
import SingleMusic from '../../components/Library/SingleMusic'
import { setCurrentSongId, setPlayingStatus } from "../../store/music/musicActions";

const RecPlayList = () => {
  const user = useSelector((state) => state.memberReducer.user); //member리듀서 가져오기
  const id = user.id; //member리듀서에서 id가져오기
  // console.log('header',user);
  const [musiclist, setMusiclist] = useState({ recMusiclistDetails: [] }); //추천 받은 리스트
  const [recMusicIds, setRecMusicIds] = useState([]); //추천 음악 id
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    const recPlayList = async () => {
      await axios
        .get(`${process.env.REACT_APP_API_BASE_URL}/recMusiclist/recent/${id}`) //추천 받은 리스트 불러오기
        .then((res) => {
          // console.log(res);
          setMusiclist(res.data); //추천 음악 리스트
          setRecMusicIds(res.data.recMusiclistDetails.map((id) => id.musicId));
          // localStorage.setItem('recMusiclist', res.data[0].img, res.data[0].reason)
        })
        .catch((err) => console.log(err));
    };
    recPlayList();
  }, []);

  //전체 저장
  const SavedPlayList = () => {
    localStorage.setItem("recMusicIds", JSON.stringify(recMusicIds));
    localStorage.setItem("recMusicTitle", musiclist.title);
    localStorage.setItem("recMusicImg", musiclist.recMusiclistDetails.img);
    // console.log(recMusicIds);
    navigate("/PlayListRename");
  };

 //개별 음악 재생
  const handleListClick = () => {
    dispatch(setMusicIds(recMusicIds));
  };

  //전체 재생
  const WholePlaying = () => {
    dispatch(setCurrentSongId(recMusicIds[0])); // 첫 번째 음악을 재생
    dispatch(setMusicIds(recMusicIds));
    dispatch(setPlayingStatus(true));
    navigate(`/MusicPlayer/${recMusicIds[0]}`);
  };

  return (
    // 추천 받은 음악 리스트
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 hide-scrollbar overflow-auto text-custom-white p-3 mb-[70px]">
      <br />
      <br />
      <p className="text-center text-[18px] mt-[20px] tracking-tight p-7 overflow-scroll h-[95px] mb-10 hide-scrollbar p-5 xl:ml-[300px] xl:mr-[300px] xs:ml-[5px] xs:mr-[5px]">
        {musiclist.reason}
      </p>

      {/* 전체 재생/ 전체 저장 버튼 */}
      <div className="flex justify-between mb-6 xs:ml-[5px] xs:mr-[5px] item-center justify-center">
        <button
          onClick={WholePlaying}
          className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple tracking-tight xs:w-[150px] xl:w-[180px]  h-10 text-[18px]"
        >
          전체 재생
        </button>
        <button
          onClick={SavedPlayList}
          className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple tracking-tight xs:w-[150px] xl:w-[180px]  h-10 text-[18px]"
        >
          전체 저장
        </button>
      </div>

      {/* 추천 받은 음악 리스트 목록 */}
      {musiclist &&
        musiclist.recMusiclistDetails.map((item) => (
          <div key={item.recMusiclistDetailId}  onClick={handleListClick}>
               <SingleMusic
                  key={item.savedMusiclistsDetailId}
                  item={{
                    musicId: item.musicId,
                    img: item.img,
                    title: item.musicTitle,
                    artist: item.musicArtist,
                    genre1: item.musicGenre,
                    time: item.musicTime,
                  }}
          />
          </div>
        ))}
    </div>
  );
};

export default RecPlayList;
