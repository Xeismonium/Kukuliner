"use client";

import React from "react";
import { InfiniteMovingCards } from "./CardTestimonials";

export function InfiniteMovingCardList() {
  return (
    <div className="pt-8 pb-4 px-4 mx-auto max-w-screen-xl text-center lg:py-8 lg:px-6">
      <div className="mx-auto max-w-screen-sm">
        <h2 className="mb-4 text-4xl tracking-tight font-extrabold text-gray-900 dark:text-white">
          The heartfelt testimonials of our user
        </h2>
        <p className="mb-8 font-light text-gray-500 lg:mb-16 sm:text-xl dark:text-gray-400">
          From life-enhancing gadgets to personalized dining experiences, and
          culinary adventures.
        </p>
      </div>
      <div className="h-fit rounded-md flex flex-col antialiased bg-white dark:bg-black dark:bg-grid-white/[0.05] items-center justify-center relative overflow-hidden">
        <InfiniteMovingCards
          items={testimonials}
          direction="right"
          speed="slow"
        />
      </div>
    </div>
  );
}

const testimonials = [
  {
    quote:
      "Kukuliner has completely changed the way I discover food! I used to struggle with deciding what to eat, but now I always find recommendations that match my taste perfectly. This app is so accurate and knows exactly what I like. Thank you, Kukuliner!",
    name: "Sarah W.",
  },
  {
    quote:
      "I am thoroughly impressed with Kukuliner. This app has helped me find new restaurants in my area that I had never tried before. The recommendations are always spot on with my preferences. Such an amazing app!",
    name: "Andy P.",
  },
  {
    quote:
      "As a food lover, Kukuliner has truly become my best friend. The app offers very precise and varied recommendations. Even when I'm traveling to new places, Kukuliner always helps me find delicious and authentic food. Incredible!",
    name: "Rina L.",
  },
  {
    quote:
      "Kukuliner is the app I've needed for so long! With personalized food recommendations, I never run out of ideas for trying something new. It's so easy to use, and the results are always satisfying. Kukuliner really makes my dining experiences more enjoyable.",
    name: "Putri AN,",
  },
  {
    quote:
      "I absolutely love the Kukuliner app! The food recommendations are always spot on and diverse. This app also helps me find hidden gem eateries that not many people know about. It's a must-have for all food enthusiasts!",
    name: "Melvinger",
  },
];
