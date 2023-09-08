import React from "react";

const Memberinfo = () => {
  return (
    <div className="bg-gradient-to-t from-gray-900 via-stone-950 to-gray-700 font-semibold tracking-tighter h-screen text-custom-white p-3">
      <div className="relative mt-[280px] text-custom-white text-xl text-center" data-te-input-wrapper-init>
        비밀번호를 입력해주세요.
      </div>
      <div>
        <input
          type="password"
          className="bg-gradient-to-t from-gray-900 tracking-tighter h-[45px] text-base border border-[rgba(253,253,253,0.10)] focus:border-custom-white pl-2 w-full mt-12 rounded-lg text-custom-white peer min-h-auto bg-transparent py-[0.32rem] leading-[1.85] outline-none transition-all duration-200 ease-linear motion-reduce:transition-none dark:text-neutral-200"
          placeholder="비밀번호를 입력해주세요."
        />
      </div>
      <div className="">
        <button
          type="submit"
          className="
            mt-7
            w-full
            px-3 h-10 relative 
            bg-[#221a38]  
            rounded-lg border border-soild border-[#fdfdfd]
            text-custom-white
            text-[16px]"
          data-te-ripple-init
          data-te-ripple-color="light"
        >
          Sumbit
        </button>
      </div>
    </div>
  );
};

export default Memberinfo;
