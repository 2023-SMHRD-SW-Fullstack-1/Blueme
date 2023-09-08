import React, { useEffect,useState } from 'react';
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

  const [recMusic, setRecMusic] = useState(null)

  // useEffect(() => {
  //   const recMusicList = async () => {
  //     try {
  //       const response = await axios.get("");
  //       setRecMusic(response.data);
  //     } catch (error) {
  //       console.error(`Error: ${error}`);
  //     }
  //   };

  //   recMusicList();
  // }, []);


  return (
    <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full'>
        <br/><br/>
        {/* ChatGPT가 추천해준 나의 플레이리스트 */}
        <div className='py-2 flex justify-between'>
            <h1 className='text-left indent-1 text-xl font-semibold tracking-tighter mt-5 '>Chat GPT가 추천해준 나의 플레이리스트</h1>  
            <Link to='/RecPlayList'><button className="flex text-custom-lightgray mt-6 mr-2 text-sm">더보기</button></Link>
        </div>
        {recMusic !== null ? 
            <Swiper direction={'vertical'} slidesPerView={4} className="h-[30%]">
              {MusicDummy.map((item) => (
                  <SwiperSlide key={item.id}>
                      <SingleMusic key={item.id} item={item} />

                  </SwiperSlide>
              ))}
            </Swiper> : <BeforeRegistration/>
        }
       
        {/* ChatGPT가 추천해준 남의 플레이리스트 */}
        <div>
            <h1 className='text-left indent-1 text-xl font-semibold tracking-tighter mt-8'>ChatGpt가 추천해준 남의 플레이리스트</h1>
            <Link to='RecPlayList'><SavedPlaylist/></Link>
        </div>

        {/* 최근에 재생한 목록 */}
        <h1 className='text-left indent-1 text-xl font-semibold tracking-tighter mt-8 mb-2'>최근에 재생한 목록</h1>  
          <Swiper direction={'vertical'} slidesPerView={2.2} className='h-[16%]'>
                {MusicDummy.map((item) => (
                    <SwiperSlide key={item.id}>
                        <SingleMusic key={item.id} item={item} />
                    </SwiperSlide>
                ))}
          </Swiper>
    </div>
  )
}

export default Main