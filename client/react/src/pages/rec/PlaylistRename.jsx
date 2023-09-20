/*
작성자: 이유영
날짜(수정포함): 2023-09-07
설명: 추천 받은 음악 제목 수정 및 전첸 저장
*/
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useSelector } from 'react-redux';
import '../../App.css'


const PlaylistRename = () => {
  const [title, setTitle] = useState('') //플레이 리스트 제목
  const navigate = useNavigate()
  const recMusicTitle = localStorage.getItem('recMusicTitle')//storage에 저장된 제목 가져오기
  const user = useSelector(state => state.memberReducer.user)//member리덕스 가져오기
  const id = user.id//member리덕스에서 id 가져오기
  // console.log('header',user);

    const SrecMusicId = localStorage.getItem('recMusicIds')//list제목
    const recMusicIds = JSON.parse(SrecMusicId)//타입 변환

    //3초 로딩 함수
    const timeout = () => {
      setTimeout(() => {
        document.getElementById('toast-warning').classList.remove("reveal")//토스트 창 소멸
        navigate('/PlaylistRemame')
      }, 2000);// 원하는 시간 ms단위로 적어주기
    };

    const SavedRecPlaylist = () => {
      const requestData = { userId : id,  title : title, musicIds : recMusicIds }
      // console.log(musicIds);
      console.log(requestData);
      axios.post(`http://172.30.1.27:8104/savedMusiclist/add`, requestData)//플리 저장요청 && 제목 수정
      .then((res) => {//응답값 userId 실패시 -1
        console.log(res)
        if(res.data > 0 ) {
          navigate('/library')
        }else {
          document.getElementById('toast-warning').classList.add("reveal")//토스트 창 생성
          timeout()
        }
        // navigate('/RecPlayList')
      }).catch((err) => console.log(err))
    }


  return (
    <div className='bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 text-custom-white p-3 h-full'>
    <br/><br/><br/>
    {/* 추천 리스트 제목 */}
      <div className='mt-52 text-center'>
          <h1 className='text-xl tracking-tight'>추천 플레이리스트의 제목을 정해주세요.</h1>
      </div>

    {/* 플레이 리스트 제목 */}
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
      {/* 토스트 창 띄우기 */}
      <div className="flex justify-center items-center">
          <div id="toast-warning" className="flex items-center border w-full fixed top-[50%] max-w-xs p-4 mb-5 text-custom-white bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800" role="alert">
            <div className="ml-3 font-normal text-center">저장에 실패했습니다.</div>
          </div>
        </div>



      

  </div>
  )
}

export default PlaylistRename