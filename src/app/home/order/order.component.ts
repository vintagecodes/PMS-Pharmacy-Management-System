import { Component, OnInit } from '@angular/core';
import { CartService } from 'cartService/cart.service';
import {AfterViewInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import { Cart } from 'src/app/cart';
import { TokenStorageService } from 'src/app/token-storage.service';
import { Order } from 'src/app/order';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';


@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent  implements OnInit {

  orderId:any;
  amount:number = 0;
  orderStatus!: boolean;
  address:any = [];
  panelOpenState = false;

  currentUser: any;
  cartList:any = [];
  abc: any = [];

  
  constructor(
    private cartService: CartService,
    private tokenStorageService: TokenStorageService, private route:Router
  ) {  
    this.currentUser = this.tokenStorageService.getUser().username;
    console.log(this.currentUser);
    
    
    this.getCartDetails();
    
  }
  ngOnInit(): void {
    this.cartService.getAddressByUsername(this.currentUser).subscribe((add)=>{
      console.log(add);
      this.address = add;
      console.log(this.address);
    });
  }



  getCartDetails() {
    this.cartService.getCartDetails(this.currentUser).subscribe((data: any) => {
      console.log(data);
      this.cartList = data;
      console.log(this.cartList);

    })
    return this.cartList;
  }

  genUniqueId(): string {
    const dateStr = Date
      .now()
      .toString(36); // convert num to base 36 and stringify
  
    const randomStr = Math
      .random()
      .toString(36)
      .substring(2, 8); // start at index 2 to skip decimal point
  
    return `${dateStr}-${randomStr}`;
  }

confirmOrder() {
  Swal.fire({
    title: 'Are you sure?',
    text: "You won't be able to revert this!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Procceed to pay'
  }).then((result) => {
    if (result.isConfirmed) {
      this.addToOrder();
      
    }
  })
}


  addToOrder(){
    this.orderId = this.genUniqueId();
    console.log(this.cartList);

    // this.amount = this.cartList.drugsCost;
    for(let i = 0; i < this.cartList.length; i++){
      this.amount += this.cartList[i].total;
      console.log(this.amount);
    }
    console.log(this.amount);
    this.orderStatus = false;
    let order = new Order(this.orderId,this.currentUser,this.cartList,this.amount, this.orderStatus, this.address);
    console.log(order);

    this.cartService.postOrder(order).subscribe((datas) => {
      console.log(datas);
      this.abc = datas;
      console.log(this.abc);
    });


    // this.cartService.pay(this.orderId);
    // {this.route.navigateByUrl('http://localhost:8080/submitPaymentDetail/'+this.orderId);}
    window.location.href=`http://localhost:8080/submitPaymentDetail/${this.orderId}`;

  }


}