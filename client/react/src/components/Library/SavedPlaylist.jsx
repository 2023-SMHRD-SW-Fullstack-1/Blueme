import React from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import dummy from '../../dummy/PlaylistDummy.json';
const SavedPlaylist = () => {
    return (
        <Swiper spaceBetween={1} slidesPerView={2}>
            {dummy.map((item) => {
                return (
                    <SwiperSlide key={item.id}>
                        <div className="flex flex-col ml-2 mr-2">
                            {/* 1. 플레이리스트 커버 */}
                            <img src={item.coverImage} alt="album cover" className="w-[180px] h-auto rounded-lg" />
                            {/* 2. 플레이리스트명 */}
                            <span className="py-2 ">{item.title}</span>
                        </div>
                    </SwiperSlide>
                );
            })}
        </Swiper>
    );
};

export default SavedPlaylist;
