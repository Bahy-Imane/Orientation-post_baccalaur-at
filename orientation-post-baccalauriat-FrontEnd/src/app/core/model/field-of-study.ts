import {Institution} from "./institution";


export interface FieldOfStudy {
  fosId: number;
  name: string;
  bacTypeRequired: string;
  minimumBacNote: number;
  departmentName: string;
  fieldOfStudyLogo:string;
  institution?: Institution;
}
