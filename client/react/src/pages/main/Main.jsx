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
설명: 나의 추천 플레이리스트, 남의 추천 플레이리스트 화면 구현 및 기능
*/
import React, { useEffect, useState } from "react";
import SavedPlaylist from "../../components/Library/SavedPlaylist";
import BeforeRegistration from "../../components/Main/BeforeRegistration";
import LikedList from "../../components/Library/LikedList";
import { Swiper, SwiperSlide } from "swiper/react";
import MusicDummy from "../../dummy/MusicDummy.json";
import SingleMusic from "../../components/Library/SingleMusic";
import { Link, useNavigate } from "react-router-dom";
import RecPlayList from "../rec/RecPlayList";
import axios from "axios";
// 리덕스 - 지희 경로변경
import { useDispatch, useSelector } from "react-redux";
import { setMusicIds, setPlayingStatus } from "../../store/music/musicActions.js";
// 미니플레이어 import
//유영 추천 음악 플레이 리스트
import SingleRecPlayList from "../../components/recommend/SingleRecPlayList";
import MyRecMusicList from '../../components/recommend/MyRecMusicList'
import OtherRecMusicList from '../../components/recommend/OtherRecMusicList'
import Play from '../../assets/img/play.png'

const Main = () => {
  const [recentlyPlayed, setRecentlyPlayed] = useState([]);
  const [myRecMusicList, setMyRecMusicList] = useState([]);
  const [otherRecMusicList, setOtherRecMusicList] = useState([]);

  // 지희 시작 (0918)
  const playingStatus = useSelector((state) => state.musicReducer.playingStatus);
  const [ids, setIds] = useState([]);
  const [otherMusicIds, setotherMusicIds] = useState([]); //남의 플리 musicId
  const [myMusicIds, setMyMusicIds] = useState([]); //나의 플리 musicId
  // const [id, setId] = useState('0');
  // const id = localStorage.getItem('id')
  const dispatch = useDispatch();
  // const musicIds = useSelector(state => state.musicReducer.musicIds);
  const user = useSelector((state) => state.memberReducer.user);
  const id = user.id;
  const isLoggendIn = useSelector((state) => state.memberReducer.isLogin);
  const navigate = useNavigate();
  // console.log('header',user);

  useEffect(() => {
    const fetchRecentlyPlayed = async () => {
      try {
        const response = await axios.get(`/playedmusic/get/${id}`);
        setRecentlyPlayed(response.data);
        // 지희(0918) - MusicIds 설정 추가
        setIds(response.data.map((music) => music.musicId));
        // 지희 끝
      } catch (error) {
        console.error(`Error: ${error}`);
      }
    };
    //남의 추천 플레이 리스트
    const otherRecPlaylist = async () => {
      try {
        if(isLoggendIn) {//로그인 한 유저 플리 숨기기
        await axios
        .get(`http://172.30.1.27:8104/recMusiclist/recent10?userId=${id}`)//남의 추천 플리 불러오기
        .then((res) => {
          console.log(res);
          setOtherRecMusicList(res.data)//남의 플레이 리스트
          setotherMusicIds(res.data.map(otherRecMusicList => otherRecMusicList.recMusiclistId))
        })
        .catch((err) => console.log(err))
      } else {//로그인 전엔 최신 10개 플리 불러오기
        await axios
        .get(`http://172.30.1.27:8104/recMusiclist/recent10`)//남의 추천 플리 불러오기
        .then((res) => {
          console.log(res);
          setOtherRecMusicList(res.data)//남의 플레이 리스트
          setotherMusicIds(res.data.map(otherRecMusicList => otherRecMusicList.recMusiclistId))
        })
        .catch((err) => console.log(err))
      }
      }catch (error) {

      }
    }

    fetchRecentlyPlayed();
    otherRecPlaylist();
  }, []);
  // console.log('other', myMusicIds);
  
  //나의 추천 플리 불러오기 => 초기값 0으로 설정 처음에 res.data가 null로 되기 때문
  useEffect(() => {
    if (isLoggendIn) {
      axios
      .get(`http://172.30.1.27:8104/recMusiclist/${id}`)//나의 추천 플리 불러오기
      .then((res) => {
        console.log(res);
        setMyRecMusicList(res.data)//나의 플레이리스트에 저장
        setMyMusicIds(res.data.map(myRecMusicList => myRecMusicList.recMusiclistId))
      })
      .catch((err) => console.log(err))
    }
  }, []);

  // 지희(0918) - MusicIds 설정
  const handleListClick = () => {
    dispatch(setMusicIds(ids));
  };

  return (
    <div className="overflow-auto mb-16 bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full pb-20 hide-scrollbar">
      <br />
      <br />
      {/* ChatGPT가 추천해준 나의 플레이리스트 */}
      <div>
        <h1 className="overflow-hidden indent-2 text-[23px] xl:ml-[50px] tracking-tight mt-[60px] w-[350px] font-semiblod">
          AI가 추천해준 나만의 플레이리스트
        </h1>
        <Swiper
        spaceBetween={10}
        slidesPerView="0"
        breakpoints={{
          320: {
            slidesPerView: 1,
            spaceBetween: 10,
          },
          400: {
            slidesPerView: 2,
            spaceBetween: 10,
          },   
          765: {
            slidesPerView: 3,
            spaceBetween: 10,
          },   
          1280: {
            slidesPerView: 5,
            spaceBetween: 5,
          }   
        }}
      >
        { id !== '0' && myRecMusicList.length !== 0 ? (
          <Swiper>
            {myRecMusicList &&
              myRecMusicList.map((item, i) => (
                <SwiperSlide key={item.recMusiclistId} >
                  <Link to= {`/RecPlayListDetail/${myMusicIds[i]}`}>
                    <MyRecMusicList key={item.musicId} item={item} myMusicIds={myMusicIds} i={i}/>
                  </Link>
                </SwiperSlide>
              ))}
          </Swiper>
        ) : (
          <BeforeRegistration />
        )}
      </Swiper>
      </div>
      {/* ChatGPT가 추천해준 남의 플레이리스트 */}
      <div>
        <h1 className="overflow-hidden  indent-2 text-[23px] xl:ml-[50px] tracking-tight mt-10 w-[240px] font-semiblod">
          Blueme가 추천해요👀
        </h1>

        <Swiper
        spaceBetween={10}
        slidesPerView="0"
        breakpoints={{
          320: {
            slidesPerView: 1,
            spaceBetween: 10,
          },
          400: {
            slidesPerView: 2,
            spaceBetween: 10,
          },   
          765: {
            slidesPerView: 3,
            spaceBetween: 10,
          },   
          1280: {
            slidesPerView: 5,
            spaceBetween: 5,
          }
        }}
      >
         {otherRecMusicList && otherRecMusicList.map((item,i) => (
                    <SwiperSlide key={item.recMusiclistId} className="">
                      <Link to={`/RecPlayListDetail/${otherMusicIds[i]}`}>
                        <OtherRecMusicList key={item.musicId} item={item} i={i}/>
                      </Link>
                    </SwiperSlide>
                ))}

        </Swiper>
      </div>

      {/* 최근에 재생한 목록 */}
      {isLoggendIn && 
      <div onClick={handleListClick}>
      <h1 className="indent-2 text-[23px] xl:ml-[50px] tracking-tight mt-10 w-[240px] font-semiblod">최근에 재생한 목록</h1>
      {/* <Swiper direction={"vertical"} slidesPerView={2} className="h-[16%]"> */}
      <div onClick={setMusicIds}>
      {recentlyPlayed.map((song) => (
        <SingleMusic key={song.id} item={song}/>
      ))}
      </div>
      </div>}
      
    </div>
  );
};

export default Main;
