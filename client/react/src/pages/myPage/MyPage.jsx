import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import logo from "../../assets/img/logo.png";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import { useLocation } from "react-router-dom";

const MyPage = () => {
  const [singers, setSingers] = useState([]);
  const location = useLocation();
  const selectedArtistsFromRecommendation =
    location.state?.selectedArtists || [];

  useEffect(() => {
    // 가상의 가수 데이터
    const dummySingers = [
      {
        id: 1,
        name: "Adele",
        image: "https://images.unsplash.com/photo-1569466896818-335b1bedfcce",
      },
      {
        id: 2,
        name: "Ed Sheeran",
        image: "https://images.unsplash.com/photo-1569466896818-335b1bedfcce",
      },
      {
        id: 3,
        name: "Taylor Swift",
        image: "https://images.unsplash.com/photo-1569466896818-335b1bedfcce",
      },
    ];

    // Merge dummySingers and selectedArtistsFromRecommendation
    setSingers([...dummySingers, ...selectedArtistsFromRecommendation]);
  }, [selectedArtistsFromRecommendation]);
  return (
    <div className="bg-custom-blue flex flex-col pl-10 text-custom-white min-h-s">
      <div className="flex justify-between items-center">
        <div className="flex items-center">
          <img src={logo} alt="" className="w-[50px] h-auto mb-3 mt-2" />
          <span className="pt-1">닉네임닉네임닉네임</span>
        </div>
        <div className="mt-3 pr-9">
          <Link to="/MemberInfoChange">
            <button className=" bg-custom-gray rounded-lg text-sm p-1">
              내 프로필
            </button>
          </Link>
        </div>
      </div>

      <div className="text-xl">내 정보</div>

      <div className="mt-2 sm:mt-2 md:mt-2">
        <input
          type="email"
          className="border-2 border-custom-gray rounded-lg bg-custom-blue text-custom-white peer block min-h-[auto] w-[90%]  bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
          placeholder="이메일을 입력해주세요."
        />
      </div>
      <div className="mt-2 sm:mt-2 md:mt-2">
        <input
          type="email"
          className="border-2 border-custom-gray rounded-lg bg-custom-blue text-custom-whitepeer block min-h-[auto] w-[90%]  bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
          placeholder="비밀번호를 입력해주세요."
        />
      </div>
      <div className="mt-2 sm:mt-2 md:mt-2">
        <input
          type="email"
          className="border-2 border-custom-gray rounded-lg bg-custom-blue text-custom-whiter-3 peer block min-h-[auto] w-[90%]  bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
          placeholder="비밀번호를 다시 입력해주세요."
        />
      </div>
      <div className="mt-2 sm:mt-2 md:mt-2">
        <input
          type="email"
          className="border-2 border-custom-gray rounded-lg bg-custom-blue text-custom-white r-3 peer block min-h-[auto] w-[90%] border-soild bg-transparent px-3 py-[0.32rem] leading-[2.15] outline-none transition-all duration-200 ease-linear   motion-reduce:transition-none dark:text-neutral-200 "
          placeholder="닉네임을 입력해주세요."
        />
        <div className="mt-5">
          <div className="text-xl mb-2">당신이 좋아하는 음악장르는?</div>
          <div className="flex items-start w-full mb-5">
            <Swiper
              spaceBetween={10}
              slidesPerView={3}
              onSlideChange={() => console.log("slide change")}
              onSwiper={(swiper) => console.log(swiper)}
              className="w-3/4"
            >
              {singers.map((singer) => (
                <SwiperSlide key={singer.id}>
                  <button class="flex flex-col items-center space-y-4 mt-1">
                    <img
                      src={singer.image}
                      alt=""
                      class="rounded-lg img-fixed"
                    />
                    <h5 class="font-semibold text-sm">{singer.name}</h5>
                  </button>
                </SwiperSlide>
              ))}
            </Swiper>
            <Link
              to="/SelectGenre"
              className="text-custom-white mr-5 self-center text-xs"
            >
              더보기
            </Link>
          </div>
        </div>
        <div className="text-xl mb-2">당신이 좋아하는 아티스트는?</div>
        <div className="flex items-start w-full mb-5">
          <Swiper
            spaceBetween={10}
            slidesPerView={3} // Reduce the number of slides per view
            onSlideChange={() => console.log("slide change")}
            onSwiper={(swiper) => console.log(swiper)}
            className="w-3/4"
          >
            {singers.map((singer) => (
              <SwiperSlide key={singer.id}>
                <button class="flex flex-col items-center space-y-4 mt-1">
                  <img src={singer.image} alt="" class="rounded-lg img-fixed" />
                  <h5 class="font-semibold text-sm">{singer.name}</h5>
                </button>
              </SwiperSlide>
            ))}
          </Swiper>
          <Link
            to="/SelectArtist"
            className="text-custom-white mr-5 self-center text-xs"
          >
            더보기
          </Link>
        </div>
      </div>
    </div>
  );
};

export default MyPage;
