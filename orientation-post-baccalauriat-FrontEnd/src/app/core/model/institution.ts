import {FieldOfStudy} from "./field-of-study";
import {Review} from "./review";
import {Student} from "./student";
import {InstitutionType} from "../enums/institution-type";

export interface Institution {
  institutionId: number;
  institutionName: string;
  description: string;
  address: string;
  website: string;
  institutionType: InstitutionType;
  // student?: Student;
  // //recommendations: Recommendation[];
  // reviews: Review[];
  // fieldOfStudies: FieldOfStudy[];
}
