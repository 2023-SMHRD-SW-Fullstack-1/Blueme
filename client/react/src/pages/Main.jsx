import React from 'react';
import SavedPlaylist from '../components/Library/SavedPlaylist';
import watch from '../assets/img/watch.png'



const Main = () => {
  return (
    <div className='bg-custom-blue text-custom-white p-3 h-full'>
        <div>
            <h1 className='text-left indent-1 text-xl font-semibold tracking-tight'>Chat GPT가 추천해주는 플레이리스트</h1>
            <div className='flex justify-center items-center p-10'>
                <img src={watch}
                alt='워치 이미지'
                class="object-cover w-[200px] h-[200px] "/>
                <div className='absolute'><h1 className='text-2xl'>워치를 등록해주세요.</h1></div>
            </div>    
        </div>
        <div>
            <h1 className='text-left indent-1 text-xl font-semibold tracking-tight'>테마별 플레이리스트</h1>
            <SavedPlaylist/>
        </div>
    </div>
  )
}

export default Main