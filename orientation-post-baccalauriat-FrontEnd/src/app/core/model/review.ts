import {Institution} from "./institution";
import {Student} from "./student";

export interface Review {
  reviewId: number;
  comment: string;
  rating: number;
  student?: Student;
  institution?: Institution;
}
