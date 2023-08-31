import React, { useRef } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowRight, faArrowLeft } from "@fortawesome/free-solid-svg-icons";
import logo from "../img/logo.png";
import "swiper/css";
import "swiper/css/effect-fade";
import "swiper/css/navigation";
import "swiper/css/pagination";

const data = [
  { id: 1, image: "https://via.placeholder.com/150", title: "Album 1" },
  { id: 2, image: "https://via.placeholder.com/150", title: "Album 2" },
  { id: 3, image: "https://via.placeholder.com/150", title: "Album 3" },
  { id: 4, image: "https://via.placeholder.com/150", title: "Album 4" },
  { id: 5, image: "https://via.placeholder.com/150", title: "Album 5" },
  { id: 6, image: "https://via.placeholder.com/150", title: "Album 6" },
  { id: 7, image: "https://via.placeholder.com/150", title: "Album 7" },
  { id: 8, image: "https://via.placeholder.com/150", title: "Album 8" },
];

function Main() {
  const swiperRef1 = useRef(null);
  const swiperRef2 = useRef(null);

  const goNext1 = () => {
    if (swiperRef1.current && swiperRef1.current.swiper) {
      swiperRef1.current.swiper.slideNext();
    }
  };

  const goPrev1 = () => {
    if (swiperRef1.current && swiperRef1.current.swiper) {
      swiperRef1.current.swiper.slidePrev();
    }
  };

  const goNext2 = () => {
    if (swiperRef2.current && swiperRef2.current.swiper) {
      swiperRef2.current.swiper.slideNext();
    }
  };

  const goPrev2 = () => {
    if (swiperRef2.current && swiperRef2.current.swiper) {
      swiperRef2.current.swiper.slidePrev();
    }
  };

  return (
    <div className=" bg-custom-blue text-white p-12 h-full  ">
      <div className="relative mb-5 text-custom-white text-center text-2xl">
        <h3>더운날 듣는 노래</h3>
        <button onClick={goPrev1} className=" prev-arrow absolute top-half left-0 z-10">
          <FontAwesomeIcon icon={faArrowLeft} />
        </button>

        <button onClick={goNext1} className="next-arrow absolute top-half right-0 z-10">
          <FontAwesomeIcon icon={faArrowRight} />
        </button>

        <Swiper
          ref={swiperRef1}
          spaceBetween={1}
          slidesPerView={4}
          slidesPerGroup={1}
          breakpoints={{
            320: {
              slidesPerView: 2,
              spaceBetween: -50,
            },
            // when window width is >= 640px
            640: {
              slidesPerView: 2,
              spaceBetween: -50,
            },
            // when window width is >= 768px
            768: {
              slidesPerView: 3,
              spaceBetween: -50,
            },
            // when window width is >=1024px
            1024: {
              slidesPerView: 4,
              spaceBetween: -50,
            },
          }}
        >
          {data.map((item) => (
            <SwiperSlide key={item.id}>
              <div className="w-full h-full flex flex-col items-center justify-center p-8 bg-white rounded-lg shadow-md">
                <img src={item.image} alt="" className="w-[100%] h-auto mb-4 rounded-lg  " />
                <p className="text-xl">{item.title}</p>
              </div>
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
      <div className="mb-5 text-custom-white text-center text-2xl">
        <h3>비오는날 듣는 노래</h3>
        <button onClick={goPrev2} className="prev-arrow absolute top-half left-0 z-10">
          <FontAwesomeIcon icon={faArrowLeft} />
        </button>

        <button onClick={goNext2} className="next-arrow absolute top-half right-0 z-10">
          <FontAwesomeIcon icon={faArrowRight} />
        </button>

        <Swiper
          ref={swiperRef2}
          spaceBetween={4}
          slidesPerView={3}
          slidesPerGroup={1}
          breakpoints={{
            320: {
              slidesPerView: 2,
              spaceBetween: -50,
            },
            // when window width is >= 640px
            640: {
              slidesPerView: 2,
              spaceBetween: -50,
            },
            // when window width is >= 768px
            768: {
              slidesPerView: 3,
              spaceBetween: -50,
            },
            // when window width is >=1024px
            1024: {
              slidesPerView: 4,
              spaceBetween: -50,
            },
          }}
        >
          {data.map((item) => (
            <SwiperSlide key={item.id}>
              <div className="w-full h-full flex flex-col items-center justify-center p-8 bg-white rounded-lg shadow-md">
                <img src={logo} alt="" className="w-[100%] h-auto mb-4 rounded-lg" />
                <p className="text-xl">{item.title}</p>
              </div>
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
    </div>
  );
}

export default Main;
