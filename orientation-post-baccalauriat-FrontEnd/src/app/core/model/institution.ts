import {Student} from "./student";
import {InstitutionType} from "../enums/institution-type";

export interface Institution {
  institutionId?: number;
  name: string;
  location: string;
  website: string;
  description: string;
  institutionType: InstitutionType;
  student?: Student;
  // recommendations: Recommendation[];
  // ratings: Rating[];
  // comments: Comment[];
  // academicUnits: AcademicUnit[];
}
