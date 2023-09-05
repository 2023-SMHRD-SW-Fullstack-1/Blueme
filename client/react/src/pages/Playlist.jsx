import React from 'react';
import SingleMusic from '../components/library/SingleMusic';
import PlaylistDummy from '../dummy/PlaylistDummy.json';
import MusicDummy from '../dummy/MusicDummy.json';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/swiper-bundle.css';

const Playlist = () => {
    return (
        <div className="bg-custom-blue text-custom-white h-full pt-20">
            <div className="flex flex-col items-center justify-center">
                <img src={PlaylistDummy[0].coverImage} className="w-[200px] h-auto rounded-lg" />
                <p className="text-2xl py-5">{PlaylistDummy[0].title}</p>
            </div>
            <div className="flex justify-between ml-3 mr-3 py-2">
                <button className="bg-custom-darkgray text-custom-lightpurple w-20">전체 재생</button>
                <button className="bg-custom-darkgray text-custom-lightpurple w-20">저장 취소</button>
            </div>
            <Swiper direction={'vertical'} slidesPerView={5} spaceBetween={1} className="h-[50%] ml-3 mr-3">
                {MusicDummy.map((item) => (
                    <SwiperSlide key={item.id}>
                        <SingleMusic key={item.id} item={item} />
                    </SwiperSlide>
                ))}
            </Swiper>
        </div>
    );
};

export default Playlist;
