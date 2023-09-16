/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: 추천 받은 음악 제목 수정
*/
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useSelector } from 'react-redux';


const PlaylistRename = () => {
  const [title, setTitle] = useState('') //플레이 리스트 제목
  const navigate = useNavigate()
  const recMusicTitle = localStorage.getItem('recMusicTitle')
  const user = useSelector(state => state.memberReducer.user)
  const id = user.id
  console.log('header',user);
    
    // 현재 날짜와 시간 구하기
    const todayTime = () => {
        let now = new Date();
        let todayYear = now.getFullYear();
        let todayMonth = now.getMonth()+1;
        let todayDate = now.getDate();
        let hours = now.getHours();
        let minutes = now.getMinutes();

        return todayYear + '년' + todayMonth + '월' + todayDate + '일' + hours + '시' + minutes +'분 당신을 위한 음악'
    }

    const SrecMusicId = localStorage.getItem('recMusicIds')
    const recMusicIds = JSON.parse(SrecMusicId)

   
    const SavedRecPlaylist = () => {
      const requestData = { userId : id,  title : title, musicIds : recMusicIds }
      // console.log(musicIds);
      console.log(requestData);
      axios.post(`http://172.30.1.27:8104/savedMusiclist/add`, requestData)
      .then((res) => {
        console.log(res)
        if(res.data > 0 ) {
          navigate('/library')
        }else {
          alert('저장에 실패했습니다.')
          navigate('/PlaylistRemame')
        }
        // navigate('/RecPlayList')
      }).catch((err) => console.log(err))
    }


  return (
    <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full'>
    <br/><br/><br/>
    {/* 추천 리스트 제목 */}
      <div className='mt-52 text-center'>
          <h1 className='text-xl font-semibold tracking-tight'>추천 플레이리스트의 제목을 정해주세요.</h1>
      </div>

    {/* 제목에 기본값으로 현재 날짜와 시간 넣어주기 */}
      <div className='mt-12'>
      <input
        type="email"
        className="focus:border-custom-white pl-2 w-full border border-soild rounded-lg bg-custom-blue text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[2.35] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200 "
        defaultValue={recMusicTitle}
        onChange={(e) => setTitle(e.target.value)}
      />
      </div>
      
      {/* 저장과 취소 버튼 */}
      <div className='flex justify-end space-x-2'>
        <div className="h-[35px] w-[53px] mt-5 border border-soild border-#FDFDFD] 
        rounded-lg bg-custom-blue text-custom-white text-sm text-center peer bg-transparent leading-[2.15] outline-none ease-linear">
            <button onClick={SavedRecPlaylist}>저장</button>
        </div>
        <div className="h-[35px] w-[53px] mt-5 border border-soild border-#FDFDFD] 
        rounded-lg bg-custom-blue text-custom-white text-sm text-center peer bg-transparent leading-[2.15] outline-none ease-linear">
             <Link to='/RecPlayList'><button>취소</button></Link>
        </div>
      </div>
      




      

  </div>
  )
}

export default PlaylistRename