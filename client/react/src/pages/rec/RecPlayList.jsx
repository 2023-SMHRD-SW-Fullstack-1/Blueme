import React, { useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import MusicDummy from '../../dummy/MusicDummy.json';
import SingleMusic from '../../components/Library/SingleMusic';
import { Link } from 'react-router-dom';

const RecPlayList = () => {

  

    return (
        // 추천 받은 음악 리스트
        <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-screen text-custom-white p-3'>
            <br/><br/><br/>
            <h1 className='text-center text-2xl font-semibold tracking-tighter mb-8 mt-5'>
                현재 당신을 위한 음악</h1>  

        {/* 전체 재생/ 전체 저장 버튼 */}
            <div className="flex justify-between mb-6">
                <button className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl">전체 재생</button>
            <Link to="/PlayListRename"><button className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl ">전체 저장</button></Link>
            </div>
        
        {/* 추천 받은 음악 리스트 목록 */}
            <Swiper direction={'vertical'} slidesPerView={9.1} className="h-[69%]">
                {MusicDummy.map((item) => (
                    <SwiperSlide key={item.id}>
                        <SingleMusic key={item.id} item={item} />

                    </SwiperSlide>
                ))}
            </Swiper>
        </div>
    );
};

export default RecPlayList;
