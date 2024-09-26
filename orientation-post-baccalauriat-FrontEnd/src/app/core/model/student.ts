import {Interest} from "../enums/interest";
import {BacType} from "../enums/bac-type";
import {Institution} from "./institution";

export interface Student {
  id?: number;
  bacScore: number;
  location: string;
  bacType: BacType;
  interest: Interest;
  institutions: Institution[];
  // ratings!: Rating[];
  // comments!: Comment[];
}
