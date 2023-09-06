import React, { useEffect } from 'react';
import SavedPlaylist from '../../components/Library/SavedPlaylist';
import BeforeRegistration from './BeforeRegistration';
import LikedList from '../../components/Library/LikedList';
import { Swiper, SwiperSlide } from 'swiper/react';
import MusicDummy from '../../dummy/MusicDummy.json';
import SingleMusic from '../../components/Library/SingleMusic';
import { Link } from "react-router-dom";
import RecPlayList from '../rec/RecPlayList';
import axios from 'axios';



const Main = () => {

 useEffect(()=>{
  
 })

  return (
    <div className='bg-custom-blue text-custom-white p-3 '>
        <br/><br/><br/>
        {/* ChatGPT가 추천해주는 플레이리스트 */}
        <div>
            <h1 className='text-left indent-1 text-xl font-semibold tracking-tighter mt-5'>Chat GPT가 추천해주는 플레이리스트</h1>
            <Link to='/RecAppDes'><BeforeRegistration/></Link>
            {/* <Link to=''><RecPlayList/></Link> */}
        </div>
        {/* 테마별 플레이리스트 */}
        <div>
            <h1 className='text-left indent-1 text-xl font-semibold tracking-tighter mt-8'>테마별 플레이리스트</h1>
            <Link to='Playlist'><RecPlayList/></Link>
        </div>
        {/* 최근에 재생한 목록 */}
        <h1 className='text-left indent-1 text-xl font-semibold tracking-tighter mt-8'>최근에 재생한 목록</h1>  
          {MusicDummy.map((item) => (
            <section key={item.id}>
              <SingleMusic key={item.id} item={item} />
            </section>
          ))}

    </div>
  )
}

export default Main