/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 추천 음악 플레이 리스트
*/
import React, { useEffect, useState } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios'
import SingleRecPlayList from './SingleRecPlayList'
import { useDispatch, useSelector } from 'react-redux';
import { setCurrentSongId } from "../../store/music/musicActions";
import { setMusicIds } from "../../store/music/musicActions.js";




const RecPlayList = () => {
    const user = useSelector(state => state.memberReducer.user)//member리듀서 가져오기
    const id = user.id//member리듀서에서 id가져오기
    // console.log('header',user);
    const [musiclist, setMusiclist] = useState({recMusiclistDetails: []}); //추천 받은 리스트
    const [recMusicIds, setRecMusicIds] = useState([])//추천 음악 id
    const navigate = useNavigate()
    const dispatch = useDispatch()

    useEffect(()=> {
        const recPlayList = async () => {
        await axios.get(`http://172.30.1.27:8104/recMusiclist/recent/${id}`)//추천 받은 리스트 불러오기
        .then((res) => {
            setMusiclist(res.data)//추천 음악 리스트
            setRecMusicIds(res.data.recMusiclistsRecent10detail.map(id => id.musicId))
            console.log(res)
            // localStorage.setItem('recMusiclist', res.data[0].img, res.data[0].reason)
        }).catch((err) => console.log(err))
    }
        recPlayList()
    }, [])

    //전체 저장
    const SavedPlayList = () => {
            for(let i = 0; i < musiclist.recMusiclistDetails.length; i++) {
                recMusicIds.push(musiclist.recMusiclistDetails[i].musicId)
            }       
            localStorage.setItem('recMusicIds', JSON.stringify(recMusicIds))
            localStorage.setItem('recMusicTitle', musiclist.title)
            // console.log(recMusicIds);
            navigate('/PlayListRename')
    }

    //전체 재생
    const WholePlaying = () => {
        dispatch(setCurrentSongId(recMusicIds[0]))
        dispatch(setMusicIds(recMusicIds))
        // console.log(i);
        navigate(`/MusicPlayer/${recMusicIds[0]}`)
    }
    
    console.log(recMusicIds);

  
    return (
        // 추천 받은 음악 리스트
        <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 hide-scrollbar overflow-auto text-custom-white p-3 mb-[70px]'>
            <br/><br/>
            <h1 className='text-center text-xl mt-[20px] tracking-tight p-7 overflow-scroll h-[95px] mb-10 hide-scrollbar p-5 xl:ml-[300px] xl:mr-[300px] xs:ml-[5px] xs:mr-[5px]'>
                {musiclist.reason}</h1>  

        {/* 전체 재생/ 전체 저장 버튼 */}
            <div className="flex justify-between mb-6 xl:ml-[300px] xl:mr-[300px] xs:ml-[5px] xs:mr-[5px] item-center justify-center">
                <button 
                onClick={WholePlaying}
                className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple tracking-tight w-[160px] h-10 text-xl">전체 재생</button>
                <button
                onClick={SavedPlayList} 
                className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple tracking-tight w-[160px] h-10 text-xl ">전체 저장</button>
            </div>
        
        {/* 추천 받은 음악 리스트 목록 */}
            {/* <Swiper direction={'vertical'} slidesPerView={8.2} className="h-[65%]"> */}
                {musiclist && musiclist.recMusiclistDetails.map((item) => (
                    <div key={item.recMusiclistDetailId} className='xl:ml-[300px] xl:mr-[300px]  xs:ml-[5px] xs:mr-[5px]'>
                        <SingleRecPlayList key={item.id} item={item} />
                    </div>
                ))}
            {/* </Swiper> */}
        </div>
    );
};

export default RecPlayList;