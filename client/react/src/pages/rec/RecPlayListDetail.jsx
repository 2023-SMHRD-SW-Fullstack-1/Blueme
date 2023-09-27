/*
작성자: 이유영
날짜(수정포함): 2023-09-18
설명: 추천 음악 플레이 리스트 디테일
*/

/*
작성자: 신지훈
날짜(수정포함): 2023-09-22
설명: 모바일 크기 조정
*/

/*
작성자: 이지희
날짜(수정포함): 2023-09-25
설명: 음악 개별 정보 컴포넌트 SingleRecPlayList -> SingleMusic으로 수정 
*/
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { Link } from "react-router-dom";
import { setCurrentSongId, setPlayingStatus } from "../../store/music/musicActions";
import { setMusicIds } from "../../store/music/musicActions.js";
import SingleMusic from "../../components/Library/SingleMusic";

const RecPlayListDetail = () => {
  const user = useSelector((state) => state.memberReducer.user); //member리듀서 가져오기
  const [musiclist, setMusiclist] = useState([]); //추천 받은 리스트
  const [recMusicIds, setRecMusicIds] = useState([]); //musicId 저장
  const navigate = useNavigate();
  const params = useParams();
  const musicId = params.id;
  // console.log(params.id);
  const dispatch = useDispatch();

  useEffect(() => {
    const recPlayList = async () => {
      //회원id가 아니라 musicId 들어가야 함
      await axios
        .get(`http://172.30.1.27:8104/recMusiclist/detail/${musicId}`) //추천 받은 리스트 불러오기
        .then((res) => {
          setMusiclist(res.data);
          // console.log(res);
          setRecMusicIds(res.data.recMusiclistsRecent10detail.map((id) => id.musicId));
        })
        .catch((err) => console.log(err));
    };
    recPlayList();
  }, []);


  //전체 저장
  const SavedPlayList = () => {
    localStorage.setItem("recMusicIds", JSON.stringify(recMusicIds));
    localStorage.setItem("recMusicTitle", musiclist.recMusiclistTitle);
    localStorage.setItem("recMusicImg", musiclist.recMusiclistsRecent10detail.img);
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
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-full text-custom-white p-3 hide-scrollbar overflow-auto mb-[70px]">
      <br />
      <br />
      <h1 className="text-center  text-xl mt-[20px] tracking-tight p-7 overflow-scroll h-[95px] mb-10 hide-scrollbar p-5 xl:ml-[100px] xl:mr-[100px]  xs:ml-[5px] xs:mr-[5px]">
        {musiclist.recMusiclistReason}
      </h1>

      {/* 전체 재생/ 전체 저장 버튼 */}
      <div className="flex justify-between mb-6 xs:ml-[5px] xs:mr-[5px] item-center justify-center">
        <button
          onClick={WholePlaying}
          className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple tracking-tight xs:w-[150px] xl:w-[180px] h-10 text-[18px]"
        >
          전체 재생
        </button>
        <button
          onClick={SavedPlayList}
          className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple  tracking-tight xs:w-[150px] xl:w-[180px] h-10 text-[18px] "
        >
          전체 저장
        </button>
      </div>

      {/* 추천 받은 음악 리스트 목록 */}
      {musiclist &&
        Array.isArray(musiclist.recMusiclistsRecent10detail) &&
        musiclist.recMusiclistsRecent10detail.map((item, i) => (
          <div
            key={item.recMusiclistDetailId}
            onClick={handleListClick}
            className="flex flex-row items-center  ml-2 mr-2 "
          >
               <SingleMusic
                  key={item.savedMusiclistsDetailId}
                  item={{
                    musicId: item.musicId,
                    img: item.img,
                    title: item.title,
                    artist: item.artist,
                    genre1: item.musicGenre,
                    time: item.musicTime,
                  }}
          />
          </div>
        ))}
        <div className="mb-[80px]"></div>

    </div>
  );
};

export default RecPlayListDetail;
