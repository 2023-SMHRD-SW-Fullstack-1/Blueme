import React, { useEffect } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import MusicDummy from '../../dummy/MusicDummy.json';
import SingleMusic from '../../components/Library/SingleMusic';
import { Link } from 'react-router-dom';

const RecPlayList = () => {

  

    return (
        <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 h-screen text-custom-white p-3'>
            <br/><br/><br/>
            <h1 className='text-center text-2xl font-semibold tracking-tighter mb-6'>
                현재 당신을 위한 음악</h1>  
            <div className="flex justify-between mb-3">
                <button className="bg-gradient-to-t from-gray-800 border ml-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl">전체 재생</button>
            <Link to="/PlayListRename"><button className="bg-gradient-to-t from-gray-800 border mr-2 rounded-lg text-custom-lightpurple font-semibold tracking-tighter w-[180px] h-10 text-xl ">전체 저장</button></Link>
            </div>
          {MusicDummy.map((item) => (
            <section key={item.id} >
              <SingleMusic key={item.id} item={item} />
            </section>
          ))}
        </div>
    );
};

export default RecPlayList;
