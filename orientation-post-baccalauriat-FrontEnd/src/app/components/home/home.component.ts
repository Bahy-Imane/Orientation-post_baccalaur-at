import { Component } from '@angular/core';
import { NgForOf } from "@angular/common";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  // communityCards = [
  //   {
  //     imgSrc: 'https://cdn.builder.io/api/v1/image/assets/TEMP/7a6958c2a7f521543f92e347b0b536cca5cec82b406fbba24cccb88b3dabc12e?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154',
  //     title: 'University Advising',
  //     description: 'Get personalized advice on selecting the right university and program for your academic and career goals.'
  //   },
  //   {
  //     imgSrc: 'https://cdn.builder.io/api/v1/image/assets/TEMP/4bb04ec6197e2d8685cbfe8cba3711d694ad395bb0ef55681cbaca3db72642b2?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154',
  //     title: 'Career Counseling',
  //     description: 'Explore various career paths and receive guidance on how to achieve your professional aspirations.'
  //   },
  //   {
  //     imgSrc: 'https://cdn.builder.io/api/v1/image/assets/TEMP/4bb04ec6197e2d8685cbfe8cba3711d694ad395bb0ef55681cbaca3db72642b2?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154',
  //     title: 'Scholarships and Grants',
  //     description: 'Find opportunities for scholarships and grants to support your education and reduce financial burdens.'
  //   }
  // ];
  //
  // achievementStats = [
  //   {
  //     imgSrc: 'https://cdn.builder.io/api/v1/image/assets/TEMP/825ae305ac1b5a7a04e8ad251e30d032fc3382a14453c3d907fc787b9131b3c6?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154',
  //     number: '150,000',
  //     label: 'Graduates'
  //   },
  //   {
  //     imgSrc: 'https://cdn.builder.io/api/v1/image/assets/TEMP/d815c4ff20b4145e42ef919dd8b23dd933d24544d0135e7f3872661fc73dd912?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154',
  //     number: '5,300',
  //     label: 'Academic Programs'
  //   },
  //   {
  //     imgSrc: 'https://cdn.builder.io/api/v1/image/assets/TEMP/57c61eaf366e04f4a77c72a5b4096dc48949ae7b1926674ff42f9189d82b2a16?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154',
  //     number: '12,000',
  //     label: 'Scholarships Awarded'
  //   },
  //   {
  //     imgSrc: 'https://cdn.builder.io/api/v1/image/assets/TEMP/47aabbb3855390ab5b5a7ba8b9f1aed2aa71f59ef3da6b785980dc767d67ad70?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154',
  //     number: '20,000',
  //     label: 'Internship Placements'
  //   }
  // ];
  //
  // updateCards = [
  //   {
  //     imgSrc: 'https://cdn.builder.io/api/v1/image/assets/TEMP/df179d0957c5f13f482ba969cfe95c1279add70506883afa292bee0c99d5ad64?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154',
  //     title: 'Explore Study Abroad Opportunities'
  //   },
  //   {
  //     imgSrc: 'https://cdn.builder.io/api/v1/image/assets/TEMP/15f879222bf32c3022887bd473e02a42d4ba51740c544b78cca3714a6f0b7394?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154',
  //     title: 'Tips for Crafting a Successful University Application'
  //   },
  //   {
  //     imgSrc: 'https://cdn.builder.io/api/v1/image/assets/TEMP/e1e3712c41fcc8aef87095071331a83a7c8c81a14b73545359f0ebc37d365ba5?placeholderIfAbsent=true&apiKey=41a2148170ee41e3aecd8c91d6b03154',
  //     title: 'Guide to Applying for Scholarships'
  //   }
  // ];
}
