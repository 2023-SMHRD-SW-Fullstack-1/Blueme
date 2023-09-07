import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const SelectArtist = () => {
  const [checkedState, setCheckedState] = useState(new Array(12).fill(false));
  const navigate = useNavigate();

  const handleOnClick = (index) => {
    const checkedCount = checkedState.filter(Boolean).length;
    if (checkedCount < 2 || checkedState[index]) {
      setCheckedState(checkedState.map((item, idx) => (idx === index ? !item : item)));
    }
  };

  const handleOnSubmit = () => {
    const checkedIndices = checkedState.reduce((indices, item, index) => [...indices, ...(item ? [index] : [])], []);

    navigate("/MyPage", { state: { selectedArtists: checkedIndices } });
  };
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tighter h-screen text-custom-white p-3">
      <h3 class="text-2xl pb-10 pt-16">당신이 좋아하는 가수는?</h3>
      <div class="grid grid-cols-3 sm:grid-cols-3 md:grid-cols-gap-x-6 gap-y-4 gap-x-5">
        {Array(12)
          .fill()
          .map((_, i) => (
            <button
              key={i}
              class="relative flex flex-col items-center space-y-1 mb-5 justify-center"
              onClick={() => handleOnClick(i)}
            >
              <img
                src={`https://randomuser.me/api/portraits/men/${i + 1}.jpg`}
                alt=""
                class="rounded-lg w-[115px] h-auto object-cover blur-sm "
              />
              <p className="absolute ">가수명</p>

              {checkedState[i] && <span className="absolute top-[30%] left-[40%] text-3xl font-bold">✔</span>}
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

export default SelectArtist;
