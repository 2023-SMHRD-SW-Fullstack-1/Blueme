import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import dummy from '../../dummy/MusicDummy.json';

const RecPlayList = () => {
    return (
        <div className='flex mt-5'>
        <Swiper spaceBetween={1} slidesPerView={2.5}>
            {dummy.map((item) => {
                return (
                    <SwiperSlide key={item.id}>
                        <div className="flex flex-col ml-2 mr-2 w-50 ">
                            {/* 1. 앨범 이미지 */}
                            <img src={item.coverImage} alt="album cover" className="w-[180px] h-auto rounded-lg" />
                            {/* 2. 제목/ 아티스트 */}
                            <span className="tracking-tighter text-sm text-center mt-2">{item.title}</span>
                            <span className="tracking-tighter text-sm text-center">{item.artist}</span>
                        </div>
                    </SwiperSlide>
                );
            })}
        </Swiper>
        </div>
    );
};

export default RecPlayList;
