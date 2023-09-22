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
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import SingleRecPlayList from "../../components/recommend/SingleRecPlayList";
import { Link } from "react-router-dom";
import Heart from "../../components/Library/Heart";
import { setCurrentSongId } from "../../store/music/musicActions";
import { setMusicIds } from "../../store/music/musicActions.js";
import DetailRecMusicList from "../../components/recommend/DetailRecMusicList";

const RecPlayListDetail = () => {
  const user = useSelector((state) => state.memberReducer.user); //member리듀서 가져오기
  const id = user.id; //member리듀서에서 id가져오기
  const [musiclist, setMusiclist] = useState([]); //추천 받은 리스트
  const [recMusicIds, setRecMusicIds] = useState([]); //musicId 저장
  const navigate = useNavigate();
  const params = useParams();
  // const musicIds = useSelector(state => state.musicReducer.musicIds);
  const musicId = params.id;
  console.log(params.id);
  const dispatch = useDispatch();

  useEffect(() => {
    // localStorage.removeItem('recMusicIds')
    const recPlayList = async () => {
      //회원id가 아니라 musicId 들어가야 함
      await axios
        .get(`http://172.30.1.27:8104/recMusiclist/detail/${musicId}`) //추천 받은 리스트 불러오기
        .then((res) => {
          setMusiclist(res.data);
          console.log(res);
          setRecMusicIds(res.data.recMusiclistsRecent10detail.map((id) => id.musicId));
        })
        .catch((err) => console.log(err));
    };
    recPlayList();
  }, []);

  console.log(recMusicIds);
  //전체 저장
  const SavedPlayList = () => {
    // localStorage.removeItem('recMusicIds')
    for (let i = 0; i < musiclist.recMusiclistsRecent10detail.length; i++) {
      recMusicIds.push(musiclist.recMusiclistsRecent10detail[i].musicId);
    }
    localStorage.setItem("recMusicIds", JSON.stringify(recMusicIds));
    localStorage.setItem("recMusicTitle", musiclist.recMusiclistTitle);
    localStorage.setItem("recMusicImg", musiclist.recMusiclistsRecent10detail.img);
    // console.log(recMusicIds);
    navigate("/PlayListRename");
  };

  //전체 재생
  const WholePlaying = () => {
    dispatch(setCurrentSongId(recMusicIds[0]));
    dispatch(setMusicIds(recMusicIds));
    // console.log(i);
    navigate(`/MusicPlayer/${recMusicIds[0]}`);
  };

  return (
    // 추천 받은 음악 리스트
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-full text-custom-white p-3 hide-scrollbar overflow-auto mb-[70px]">
      <br />
      <br />
      <h1 className="text-center text-xl mt-[20px] tracking-tight p-7 overflow-scroll h-[95px] mb-10 hide-scrollbar p-5 xl:ml-[300px] xl:mr-[300px]  xs:ml-[5px] xs:mr-[5px]">
        {musiclist.recMusiclistReason}
      </h1>

      {/* 전체 재생/ 전체 저장 버튼 */}
      <div className="flex justify-between mb-6 xl:ml-[300px] xl:mr-[300px] xs:ml-[5px] xs:mr-[5px] item-center justify-center">
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
      {/* <Swiper direction={'vertical'} slidesPerView={8.2} className="h-[65%]"> */}
      {musiclist &&
        Array.isArray(musiclist.recMusiclistsRecent10detail) &&
        musiclist.recMusiclistsRecent10detail.map((item, i) => (
          <div
            key={item.recMusiclistDetailId}
            className="flex flex-row items-center ml-2 mr-2 xl:ml-[300px] xl:mr-[300px] xs:ml-[5px] xs:mr-[5px]"
          >
            <Link
              to={`/MusicPlayer/${recMusicIds[i]}`}
              onClick={() => {
                dispatch(setCurrentSongId(recMusicIds[i]));
                dispatch(setMusicIds(recMusicIds));
              }}
              className="flex-grow"
            >
              <DetailRecMusicList key={item.id} item={item} />
            </Link>
            <div className="ml-auto  xs:ml-[5px] xs:mr-[5px]">
              {/* 하트 컴포넌트 */}
              {/* {item.musicId && ( */}
              <Heart key={item.id} item={item.musicId} />
              {/* )} */}
            </div>
          </div>
        ))}
      {/* </Swiper> */}
    </div>
  );
};

export default RecPlayListDetail;
