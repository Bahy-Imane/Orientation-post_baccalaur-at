import {BacType} from "../enums/bac-type";
import {Interest} from "../enums/interest";
import {UserDto} from "./user-dto";

export class SignUpDto extends UserDto{

  bacScore!: number;
  location!: string;
  bacType!: BacType;
  interest!: Interest;
}
