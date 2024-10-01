import {BacType} from "../enums/bac-type";
import {UserDto} from "./user-dto";

export interface SignUpDto extends UserDto {

  dateOfBirth: string;
  bacScore: number;
  location: string;
  bacType: BacType;
}
