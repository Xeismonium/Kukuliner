"use client";
import React from "react";
import { StickyScroll } from "./feature-scroll-reveal";
import Image from "next/image";

import localBased from "../../../public/maps.jpg";
import desiredBased from "../../../public/personalization.jpg";
import localRestaurant from "../../../public/restaurant.jpeg";
import foodList from "../../../public/foodList.webp";

const content = [
  {
    title: "Desire-Based Personalized Recommendations",
    description:
      "Discover food that perfectly matches your cravings and preferences. Kukuliner makes it easy for you to search for the food you desire. Whether you're in the mood for something sweet, savory, or exotic, Kukuliner simplifies your search and helps you find exactly what you want.",
    content: (
      <div className="h-full w-full flex items-center justify-center text-white">
        <Image
          src={desiredBased}
          width={300}
          height={300}
          className="h-full w-full object-cover"
          alt="Desire-Based Personalized Recommendations image"
        />
      </div>
    ),
  },
  {
    title: "Location-Based Personalized Recommendations",
    description:
      "Explore the best culinary experiences around you, tailored to your location. Kukuliner provides personalized recommendations based on your current location, highlighting nearby restaurants and eateries that suit your taste. From hidden local gems to popular hotspots, you'll always find the perfect place to dine, no matter where you are.",
    content: (
      <div className="h-full w-full flex items-center justify-center text-white">
        <Image
          src={localBased}
          width={300}
          height={300}
          className="h-full w-full object-cover"
          alt="Local Based Recommendations image"
        />
      </div>
    ),
  },
  {
    title: "Local Restaurant Finder",
    description:
      "Easily discover the best local restaurants around you, including popular spots and hidden gems.",
    content: (
      <div className="h-full w-full  flex items-center justify-center text-white">
        <Image
          src={localRestaurant}
          width={300}
          height={300}
          className="h-full w-full object-cover"
          alt="Local Restaurant Finder image"
        />
      </div>
    ),
  },
  {
    title: "Curated Food Lists",
    description:
      "Enjoy specially curated food lists, such as local favorites and more.",
    content: (
      <div className="h-full w-full  flex items-center justify-center text-white">
        <Image
          src={foodList}
          width={300}
          height={300}
          className="h-full w-full object-cover"
          alt="Curated Food Lists image"
        />
      </div>
    ),
  },
];
export function Feature() {
  return (
    <div className="pt-8 pb-4 px-4 mx-auto max-w-screen-xl text-center lg:py-8 lg:px-6">
      <div className="mx-auto max-w-screen-sm">
        <h2 className="mb-4 text-4xl tracking-tight font-extrabold text-gray-900 dark:text-white">
          What We Offer
        </h2>
      </div>
      <div className="p-10">
        <StickyScroll content={content} />
      </div>
    </div>
  );
}
