"use client";

import React from "react";
import { AuroraBackground } from "./aurora-background";
import { motion } from "framer-motion";
import Link from "next/link";
import Image from "next/image";

import HeroImage from "../../../public/hero.png";

export default function HeroSection() {
  return (
    <AuroraBackground>
      <motion.div
        initial={{ opacity: 0.0, y: 40 }}
        whileInView={{ opacity: 1, y: 0 }}
        transition={{
          delay: 0.3,
          duration: 0.8,
          ease: "easeInOut",
        }}
        className="relative flex flex-col gap-4 items-center justify-center px-4 w-ful"
      >
        <section id="home" className="pt-[100px]">
          <div className="container lg:max-w-[1305px] lg:px-10">
            <div className="-mx-4 flex flex-wrap items-center">
              <div className="w-full px-4 lg:w-7/12">
                <div className="wow fadeInUp mb-12 lg:mb-0 lg:max-w-[570px]">
                  <span className="mb-5 block text-lg font-medium leading-tight text-black dark:text-white sm:text-[22px] xl:text-[22px]">
                    Discover Your Favorite Dish
                  </span>
                  <h1 className="mb-6 text-3xl font-bold leading-tight text-black dark:text-white sm:text-[40px] md:text-[50px] lg:text-[42px] xl:text-[50px]">
                    Kukuliner: Your Personal AI Foodie
                  </h1>
                  <p className="mb-10 max-w-[475px] text-base leading-relaxed text-body dark:text-white">
                    Kukuliner is an innovative AI-powered app that curates
                    personalized food recommendations based on your tastes and
                    location. Kukuliner guides you to delicious discoveries
                    every time.
                  </p>
                  <div className="flex flex-wrap items-center">
                    <Link
                      href="/"
                      className="mb-6 mr-6 inline-flex h-[60px] items-center rounded-lg bg-black px-[30px] py-[14px] text-white hover:bg-opacity-90 dark:bg-white dark:text-black dark:hover:bg-opacity-90"
                    >
                      <span className="mr-[18px] border-stroke border-opacity-40 pr-[18px] leading-relaxed dark:border-[#BDBDBD]">
                        Download Now
                      </span>

                      <span>
                        <svg
                          xmlns="http://www.w3.org/2000/svg"
                          width="0.91em"
                          height="1em"
                          viewBox="0 0 256 283"
                        >
                          <path
                            fill="#ea4335"
                            d="M119.553 134.916L1.06 259.061a32.14 32.14 0 0 0 47.062 19.071l133.327-75.934z"
                          />
                          <path
                            fill="#fbbc04"
                            d="M239.37 113.814L181.715 80.79l-64.898 56.95l65.162 64.28l57.216-32.67a31.345 31.345 0 0 0 0-55.537z"
                          />
                          <path
                            fill="#4285f4"
                            d="M1.06 23.487A30.6 30.6 0 0 0 0 31.61v219.327a32.3 32.3 0 0 0 1.06 8.124l122.555-120.966z"
                          />
                          <path
                            fill="#34a853"
                            d="m120.436 141.274l61.278-60.483L48.564 4.503A32.85 32.85 0 0 0 32.051 0C17.644-.028 4.978 9.534 1.06 23.399z"
                          />
                        </svg>
                      </span>
                    </Link>

                    <Link
                      href="https://youtu.be/ki-kPVwSxyY"
                      target="_blank"
                      className="glightbox mb-6 inline-flex items-center py-4 text-black hover:text-primary dark:text-white dark:hover:text-primary"
                    >
                      <span className="mr-[22px] flex h-[60px] w-[60px] items-center justify-center rounded-full border-2 border-current">
                        <svg
                          width="14"
                          height="16"
                          viewBox="0 0 14 16"
                          fill="none"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <path
                            d="M13.5 7.06367C14.1667 7.44857 14.1667 8.41082 13.5 8.79572L1.5 15.7239C0.833334 16.1088 -3.3649e-08 15.6277 0 14.8579L6.05683e-07 1.00149C6.39332e-07 0.231693 0.833334 -0.249434 1.5 0.135466L13.5 7.06367Z"
                            fill="currentColor"
                          ></path>
                        </svg>
                      </span>
                      <span className="text-base font-medium">
                        <span className="block text-sm"> Watch Demo </span>
                        See how it works
                      </span>
                    </Link>
                  </div>
                </div>
              </div>
              <div className="w-full px-4 lg:w-5/12 hidden md:block">
                <div className="wow fadeInUp relative z-10 mx-auto w-full max-w-[530px] lg:mr-0">
                  <Image
                    src={HeroImage}
                    alt="Hero image"
                    loading="lazy"
                    width={250}
                    height={250}
                    decoding="async"
                  />
                </div>
              </div>
            </div>
          </div>
        </section>
      </motion.div>
    </AuroraBackground>
  );
}
