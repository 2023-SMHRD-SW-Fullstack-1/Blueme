/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 아티스트 선택/ 수정 /검색
*/
import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios, { AxiosError } from 'axios'
import { useSelector } from "react-redux";
import '../../App.css'
import Artist from '../../components/MyPage/Artist'


const SelectArtist = () => {

  const navigate = useNavigate();
  const [artist, setArtist] = useState([])
  const [checkedState, setCheckedState] = useState([]);
  const [artistInput, setArtistInput] = useState('')
  const [searchArtist, setSearchArtist] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const user = useSelector(state => state.memberReducer.user)
  const id = localStorage.getItem('id')
  const location = useLocation()
  // const snsId = localStorage.setitem('id')
  // console.log('snsId', id);


    //3초 로딩 함수
   const timeout = () => {
    setTimeout(() => {
      document.getElementById('toast-warning').classList.remove("reveal")//3초 후 토스트창 닫기
    }, 3000);// 원하는 시간 ms단위로 적어주기
    };

    const searchTime = () => {
      setTimeout(() => {
         document.getElementById('search-warning').style.display = "none"//3초 후 토스트창 닫기
      }, 3000)
    }

    //아티스트 한 개 선택 시 마다 호출됨
    const handleOnClick = (artistId) => {
        if(checkedState.includes(artistId)) {//같은 아티스트 id고르면 아닌 것만 추가
            setCheckedState((prev) => prev.filter((itemId) => itemId !== artistId));
        } else if(checkedState.length < 2) {//2개 이하로 고른 경우 추가
            setCheckedState((prev) => [...prev, artistId]);
        } else {
            // 체크 상태가 이미 두 개인 경우 가장 오래된 체크 상태를 제거하고 새로운 것을 추가
            setCheckedState(([_, ...rest]) => [...rest, artistId]);
        }
    };


    //화면 로딩 시 전체 아티스트 데이터 불러오기
    useEffect(() => {
        axios.get(`http://localhost:8104/Artistrecommend`)
        .then((res) => {
            // console.log(res);
            setArtist(res.data) //artist에 데이터 삽입
            setIsLoading(false)
        })
        .catch((err) => console.log(err))
    }, [])


    //회원가입 시 아티스트 선택(2개)
    const handleSelect = () => {
        if(checkedState.length === 2) {//아티스트 2명 선택 시 
          const  requestData = {artistIds : checkedState ,favChecklistId : id}
          axios.post(`${process.env.REACT_APP_API_BASE_URL}/SaveFavArtist`,requestData)//아티스트 저장 성공 여부
          .then((res) => {//성공 실패 시 반환값 -1
              if(res.data > 0) {
                if(user.email === null) {//회원가입 시
                  navigate('/JoinComplete')
                }else {//수정 시
                  navigate('/MyPage')
                }
              }else if(res.data == -1) {
                alert('저장되지 않았습니다. 다시 선택해주세요.')
                navigate('./Artistrecommend')
              }   
              // console.log(res)
          }).catch((err) => console.log(err))

      } else {//2명 이하 선택 시
        document.getElementById('toast-warning').classList.add("reveal")//토스트 창 띄우기
        timeout()
        navigate('/Artistrecommend')
      }
    };
    
    
    //아티스트 검색 => 검색 후 입력값 지우면 전체 리스트 보여지도록
    const handleArtist = async () => {
      if(artistInput === '') {
        axios.get(`${process.env.REACT_APP_API_BASE_URL}/Artistrecommend`)
        .then((res) => {
            // console.log(res);
            setSearchArtist(res.data)
            setIsLoading(false)
        })
        .catch((err) => console.log(err))
      }else {
       await axios.get(`${process.env.REACT_APP_API_BASE_URL}/searchArtist/${artistInput}`)
        .then((res) => {
          setSearchArtist(res.data)
          // console.log('검색', res)
        }).catch((err) => {
          if(err.response.data == '') {
            document.getElementById('search-warning').style.display = 'block'//토스트 창 띄우기
            searchTime()
            navigate('/Artistrecommend')
          }
            console.log(err);
            

          

        })
      }
    }


    // 아티스트 입력 후 입력한 키가 Enter일 경우 버튼 클릭한 것과 동일한 동작 실행
    const activeEnter = (e) => {
      if (e.key == 'Enter') {
          handleArtist()   // Enter 입력이 되면 클릭 이벤트 실행  
      }
    }

     //아티스트 수정하기
     const handelUpdate = () => {
      if(checkedState.length === 2) {
        const requestData = {artistIds : checkedState ,favChecklistId : id}
          // console.log('requestData',requestData);
          axios
          .patch(`${process.env.REACT_APP_API_BASE_URL}/updateFavArtist`,requestData)//선택한 아티스트 저장 성공 여부
          .then((res) => {//저장 실패일 경우 반환값 -1
            if(res.data >0) {
              navigate('/MyPage')
            }else if(res.data == -1) {
              alert('저장되지 않았습니다. 다시 선택해주세요.')
              navigate('./Artistrecommend')
            }   
            // console.log(res)
          })
          .catch((err) => console.log(err))
          // console.log(checkedState);
    } else{
        document.getElementById('toast-warning').classList.add("reveal")
        timeout()
        navigate('/Artistrecommend')
    }
    }


  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 tracking-tight overflow-auto hide-scrollbar text-custom-white p-3">
      <h3 className="text-2xl pt-[90px] lg:ml-[100px] ">당신이 좋아하는 아티스트는?</h3>
      {/* 아티스트 검색 */}
      <div className="text-center item-center w-[350px] mt-[20px] lg:ml-[100px] justify-center">
        <input
              type="text"
              onChange={(e) => setArtistInput(e.target.value)}
              className="bg-[#404752] placeholder:italic placeholder:text-slate-400 block w-full rounded-md py-2 pl-9 pr-3 shadow-sm focus:outline-none focus:border-sky-500 focus:ring-white focus:ring sm:text-sm"
              placeholder="아티스트를 입력해주세요."
              onKeyDown={activeEnter}
            />
      </div>

      {/* 검색한 아티스트 */}
      <div className="mb-[100px] grid md:grid-cols-4 sm:grid-cols-2 xs:grid-cols-2 md:grid-cols-gap-x-6 gap-y-1 gap-y-4 gap-x-5 mt-8 justify-center">
      
      {isLoading ? (<p>Loading...</p>) : 

      searchArtist && searchArtist.length > 0 ? (
        searchArtist.map((searchArtist, artist) => (
            <button
              key={searchArtist.artistFilePath}
              className="relative flex flex-col items-center space-y-1 mb-5 justify-center"
              onClick={()=> handleOnClick(searchArtist.artistFilePath)}
            >
            <img
              src={"data:image/;base64," + searchArtist.img}
              alt="genre img"
              className="rounded-lg w-[180px] h-[175px] h-auto object-cover blur-[1.5px]"
            />
            <p className="absolute text-2xl w-[180px]">{searchArtist.artistName}</p>
            {checkedState.includes(searchArtist.artistFilePath) && (
              <span className="absolute top-[25%] left-[40%] text-7xl font-bold text-black">
                ✔
              </span>
            )} 
          </button>
          ))
        ) 
        : 
        // 전체 아티스트 
        (artist.map((artist) => (
            <button
              key={artist.artistFilePath}
              className="relative flex flex-col items-center space-y-1 mb-5 justify-center"
              onClick={()=> handleOnClick(artist.artistFilePath)}
            >
            <Artist key={artist.artistId} artist={artist} checkedState={checkedState}/>
          </button>
        ))
        )
      }
      </div>
      {user.email === null ?
      <button
      onClick={handleSelect}
      className="
          mt-5
          w-full
          px-3 h-[60px]
          bg-[#221a38]  
          rounded-lg border border-soild border-[#fdfdfd]
          text-custom-white
          text-[20px]
          mb-[65px]
          bottom-0
          fixed left-0 right-0 mx-auto
        "
        
    >
      선택하기
    </button> : <button
          onClick={handelUpdate}
          className="
            mt-5
            w-full
            px-3 h-[60px]
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white
            text-[20px]
            mb-[65px]
            bottom-0
            fixed left-0 right-0 mx-auto
            "
        >
          수정하기
        </button>}
     <div/>


    {/* 토스트 창 띄우기 */}
    <div className="flex justify-center items-center">
        <div id="toast-warning" className="flex border w-full fixed top-[50%] max-w-xs p-4 mb-5 text-custom-white bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800" role="alert">
           <div className="ml-3 font-normal text-center">아티스트 2명을 선택해주세요.</div>
        </div>
    </div>

     {/* 토스트 창 띄우기 */}
     <div className="flex justify-center items-center">
        <div id="search-warning" 
        className="flex border w-full fixed top-[50%] max-w-xs p-4 mb-5 text-custom-white bg-gray-900 via-stone-950 to-gray-700 rounded-lg shadow dark:text-gray-400 dark:bg-gray-800" role="alert" style={{display: 'none'}}>
           <div className="ml-3 font-normal text-center">해당하는 아티스트가 없습니다.</div>
        </div>
     </div>

    </div>
  );
};

export default SelectArtist;