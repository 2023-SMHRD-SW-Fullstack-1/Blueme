/*
작성자: 이유영
날짜(수정포함): 2023-09-13
설명: 아티스트 선택/ 수정 /검색
*/
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios'


const SelectArtist = () => {

  const navigate = useNavigate();
  const [artist, setArtist] = useState([])
  const [checkedState, setCheckedState] = useState([]);
  const [artistInput, setArtistInput] = useState('')
  const [searchArtist, setSearchArtist] = useState([])
  const [isLoading, setIsLoading] = useState(true)
  const id = localStorage.getItem('id')


    //아티스트 한 개 선택 시 마다 호출됨
    const handleOnClick = (artistId) => {
        if(checkedState.includes(artistId)) {
            setCheckedState((prev) => prev.filter((itemId) => itemId !== artistId));
        } else if(checkedState.length < 2) {
            setCheckedState((prev) => [...prev, artistId]);
        } else {
            // 체크 상태가 이미 두 개인 경우 가장 오래된 체크 상태를 제거하고 새로운 것을 추가
            setCheckedState(([_, ...rest]) => [...rest, artistId]);
        }
    };


    //화면 로딩 시 전체 아티스트 데이터 불러오기
    useEffect(() => {
        axios.get("http://172.30.1.45:8104/Artistrecommend")
        .then((res) => {
            console.log(res);
            setArtist(res.data) //artist에 데이터 삽입
            setIsLoading(false)
        })
        .catch((err) => console.log(err))
    }, [])


    //회원가입 시 아티스트 선택(2개)
    const handleSelect = () => {
        if(checkedState.length === 2) {//아티스트 2명 선택 시 
          localStorage.setItem('selectArtist1', checkedState[0])
          localStorage.setItem('selectArtist2', checkedState[1])
          const requestData = {artistIds : checkedState ,favChecklistId : id}

          axios.post("http://172.30.1.45:8104/SaveFavArtist",requestData)
          .then((res) => {
              if(res.data > 0) {
                navigate('/JoinComplete')
              }else if(res.data == -1) {
                alert('저장되지 않았습니다. 다시 선택해주세요.')
                navigate('./Artistrecommend')
              }   
              console.log(res)
          }).catch((err) => console.log(err))

      } else {//2명 이하 선택 시
          alert('선호하는 아티스트 2명을 선택해주세요.')
          navigate('/Artistrecommend')
      }
    };


    //아티스트 수정
    const handleUpdate = () => {
      if(checkedState.length === 2) {
        localStorage.setItem('selectArtist1', checkedState[0])
        localStorage.setItem('selectArtist2', checkedState[1])
        // localStorage.setItem('selectGenre', JSON.stringify(checkedState));
        const requestData = {artistIds : checkedState ,favChecklistId : id}
        console.log(requestData);
        axios.post("http://172.30.1.45:8104/SaveFavArtist",requestData)
        .then((res) => {
            if(res.data > 0) {
              navigate('/MyPage')
            }else if(res.data == -1) {
              alert('저장되지 않았습니다. 다시 선택해주세요.')
              navigate('./Artistrecommend')
            }   
            console.log(res)
        }).catch((err) => console.log(err))
      } else {
          alert('선호하는 아티스트 2명을 선택해주세요.')
          navigate('/Artistrecommend')
      }
    };
    
    
    //아티스트 검색 => 검색 후 입력값 지우면 전체 리스트 보여지도록
    const handleArtist = async () => { 
      const filterArtist = artist.filter((artist) => {
        return artist.artistName.includes(artistInput)
      })
      // console.log('안',filterArtist);
      if(artistInput == '') {
        axios.get("http://172.30.1.45:8104/Artistrecommend")
        .then((res) => {
            console.log(res);
            setSearchArtist(res.data)
            setIsLoading(false)
        })
        .catch((err) => console.log(err))
      }else {
       await axios.get(`http://172.30.1.45:8104/searchArtist/${artistInput}`)
        .then((res) => {
          setSearchArtist(res.data)
          console.log('검색', res)
        }).catch((err) => console.log(err))
        console.log(artistInput);
      }
    }


    // 아티스트 입력 후 입력한 키가 Enter일 경우 버튼 클릭한 것과 동일한 동작 실행
    const activeEnter = (e) => {
      if (e.key == 'Enter') {
          handleArtist()   // Enter 입력이 되면 클릭 이벤트 실행  
      }
    }


  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tight overflow-auto hide-scrollbar text-custom-white p-3">
      <h3 className="text-3xl pt-[80px]">당신이 좋아하는 아티스트는?</h3>
      {/* 아티스트 검색 */}
      <div className="text-center item-center">
        <input
              type="text"
              onChange={(e) => setArtistInput(e.target.value)}
              className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 mr-3 w-[320px] mt-5 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
              placeholder="아티스트를 입력해주세요."
              onKeyDown={activeEnter}
            />
        <button
        className="bg-gradient-to-t from-gray-900 h-[45px] text-base tracking-tight border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 mt-4 w-[55px] rounded-lg text-custom-white peer bg-transparent py-[0.42rem] leading-[1.65] outline-none transition-all "
        onClick={handleArtist}><span className="mr-2">검색</span></button>
      </div>

      {/* 검색한 아티스트 */}
      <div className="grid grid-cols-2 sm:grid-cols-2 md:grid-cols-gap-x-6 gap-y-1 gap-y-4 gap-x-5 mt-8">
      
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
            <p className="absolute text-2xl">{searchArtist.artistName}</p>
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
            <img
              src={"data:image/;base64," + artist.img}
              alt="genre img"
              className="rounded-lg w-[180px] h-[175px] h-auto object-cover blur-[1.5px]"
            />
            <p className="absolute text-2xl">{artist.artistName}</p>
            {checkedState.includes(artist.artistFilePath) && (
              <span className="absolute top-[25%] left-[40%] text-7xl font-bold text-black">
                ✔
              </span>
            )}
          </button>
        ))
        )
      }
      </div>
      {localStorage.getItem('email') === null ?
      <button
      onClick={handleSelect}
      className="
        mt-5
        w-full
        px-3 h-10 relative 
        bg-[#221a38]  
        rounded-lg border border-soild border-[#fdfdfd]
        text-custom-white
        text-[16px]
        mb-[80px]"
        
    >
      선택하기
    </button> : <button
          onClick={handleUpdate}
          className="
            mt-5
            w-full
            px-3 h-10 relative 
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white
            text-[16px]
            mb-[80px]"
        >
          수정하기
        </button>}
     <div/>
    </div>
  );
};

export default SelectArtist;