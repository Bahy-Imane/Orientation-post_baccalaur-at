import {Institution} from "../model/institution";
import {Student} from "../model/student";

export interface ReviewDto {
  comment: string;
  rating: number;
  student: Student;
  institution: Institution;
}
