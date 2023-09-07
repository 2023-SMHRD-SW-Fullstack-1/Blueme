// SelectArtist.jsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const SelectGenre = () => {
  const dummySingers = [
    {
      id: 1,
      name: "Adele",
      image: `https://randomuser.me/api/portraits/men/1.jpg`,
    },
    {
      id: 2,
      name: "Adele1",
      image: `https://randomuser.me/api/portraits/men/2.jpg`,
    },
    {
      id: 3,
      name: "Adele2",
      image: `https://randomuser.me/api/portraits/men/3.jpg`,
    },
    {
      id: 4,
      name: "Adele",
      image: `https://randomuser.me/api/portraits/men/4.jpg`,
    },
    {
      id: 5,
      name: "Adele1",
      image: `https://randomuser.me/api/portraits/men/5.jpg`,
    },
    {
      id: 6,
      name: "Adele2",
      image: `https://randomuser.me/api/portraits/men/6.jpg`,
    },
    {
      id: 7,
      name: "Adele",
      image: `https://randomuser.me/api/portraits/men/7.jpg`,
    },
    {
      id: 8,
      name: "Adele1",
      image: `https://randomuser.me/api/portraits/men/8.jpg`,
    },
    {
      id: 9,
      name: "Adele2",
      image: `https://randomuser.me/api/portraits/men/9.jpg`,
    },
    {
      id: 10,
      name: "Adele",
      image: `https://randomuser.me/api/portraits/men/10.jpg`,
    },
    {
      id: 11,
      name: "Adele1",
      image: `https://randomuser.me/api/portraits/men/11.jpg`,
    },
    {
      id: 12,
      name: "Adele2",
      image: `https://randomuser.me/api/portraits/men/12.jpg`,
    },
  ];

  const [checkedState, setCheckedState] = useState(
    new Array(dummySingers.length).fill(false)
  );
  const navigate = useNavigate();

  const handleOnClick = (index) => {
    const checkedCount = checkedState.filter(Boolean).length;
    if (checkedCount < 2 || checkedState[index]) {
      setCheckedState(
        checkedState.map((item, idx) => (idx === index ? !item : item))
      );
    }
  };

  const handleOnSubmit = () => {
    const selectedArtists = dummySingers.filter(
      (_, index) => checkedState[index]
    );

    navigate("/MyPage", { state: { selectedArtists } });
  };

  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tighter h-screen text-custom-white p-3">
      <h3 class="text-2xl pb-10 pt-16 mt-3">당신이 좋아하는 장르는?</h3>
      <div class="grid grid-cols-3 sm:grid-cols-3 md:grid-cols-gap-x-6 gap-y-1 gap-y-4 gap-x-5">
        {dummySingers.map((singer, i) => (
          <button
            key={i}
            class="relative flex flex-col items-center space-y-1 mb-5 justify-center"
            onClick={() => handleOnClick(i)}
          >
            <img
              src={singer.image}
              alt=""
              class="rounded-lg w-[115px] h-auto object-cover blur-sm"
            />
            <p className="absolute ">장르명</p>

            {checkedState[i] && (
              <span className="absolute top-[30%] left-[40%] text-xl font-bold">
                ✔
              </span>
            )}
          </button>
        ))}
      </div>
      <div>
        <button
          onClick={handleOnSubmit}
          className="
            mt-5
            w-full
            px-3 h-10 relative 
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white
            text-[16px]"
        >
          수정하기
        </button>
      </div>
    </div>
  );
};

export default SelectGenre;

// const handleOnClick = (index) => {
//     const checkedCount = checkedState.filter(Boolean).length;
//     if (checkedCount < 2 || checkedState[index]) {
//     setCheckedState(checkedState.map((item, idx) => (idx === index ? !item : item)));
//     }
//     //
