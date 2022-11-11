import { Component, OnInit, VERSION, ViewChild } from '@angular/core';
//import { NgxCsvParser, NgxCSVParserError } from 'ngx-csv-parser';

@Component({
  selector: 'app-csvparser',
  templateUrl: './csvparser.component.html',
  styleUrls: ['./csvparser.component.css']
})
export class CsvparserComponent implements  OnInit{
  name = "Angular " + VERSION.major;
    
  lines: any = [];
  linesR: any = [];

  ngOnInit() {}

  changeListener(files:any) {
    let fileList = (<HTMLInputElement>files.target).files;
    if (fileList && fileList.length > 0) {
      let file: File = fileList[0];
      console.log(file.name);
      console.log(file.size);
      console.log(file.type);

      let reader: FileReader = new FileReader();
      reader.readAsText(file);
      console.log(reader)
      reader.onload = e => {
        let csv: any = reader.result;
        let allTextLines = [];
        allTextLines = csv.split(/\r|\n|\r/);
        let headers = allTextLines[0].split(";");
        let data = headers;
        let tarr = [];
        for (let j = 0; j < headers.length; j++) {
          tarr.push(data[j]);
        }
        this.lines.push(tarr);
        let tarrR = [];
        let arrl = allTextLines.length;
        let rows = [];
        for (let i = 1; i < arrl; i++) {
          rows.push(allTextLines[i].split(";"));
        }
        for (let j = 0; j < arrl; j++) {
          tarrR.push(rows[j]);
        }
        this.linesR.push(tarrR);
      };
    }
  }
}

 



