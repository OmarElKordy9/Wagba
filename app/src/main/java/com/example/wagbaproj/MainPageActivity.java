package com.example.wagbaproj;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wagbaproj.Adaptors.PopularAdaptor;
import com.example.wagbaproj.Adaptors.RestaurantsAdaptor;
import com.example.wagbaproj.Models.DishesModel;
import com.example.wagbaproj.Models.RestaurantsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterRestaurants, adapterPopular;
    private RecyclerView recyclerRestaurantsList, recyclerPopularList;
    ImageView userProfileButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        userProfileButton = findViewById(R.id.userProfile);

        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, UserActivity .class);
                startActivity(intent);
            }
        });

        recyclerRestaurants();
        recyclerPopular();
        navigationBar();
    }


    private void navigationBar(){
        FloatingActionButton floatingActionButton = findViewById(R.id.cartButton);
        LinearLayout homeButton = findViewById(R.id.homeButton);
        LinearLayout logoutButton = findViewById(R.id.logoutButton);
        LinearLayout ordersButton = findViewById(R.id.ordersButton);
        LinearLayout trackOrderButton = findViewById(R.id.trackOrderButton);

        trackOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, CurrentOrdersActivity.class);
                startActivity(intent);
            }
        });

        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, OrdersActivity.class);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, MainPageActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPageActivity.this, SignInActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

    }

    private void recyclerRestaurants(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerRestaurantsList = findViewById(R.id.recyclerRestaurants);
        recyclerRestaurantsList.setLayoutManager(linearLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference restaurantsRef = database.getReference("Restaurants");

        ArrayList<RestaurantsModel> restaurants = new ArrayList<>();

        restaurantsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                restaurants.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    restaurants.add(postSnapshot.getValue(RestaurantsModel.class));
                }
                adapterRestaurants = new RestaurantsAdaptor(restaurants);
                recyclerRestaurantsList.setAdapter(adapterRestaurants);
                recyclerRestaurantsList.setLayoutManager(linearLayoutManager);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Failure", "Failed");
            }
        });

//        ArrayList<DishesModel> kfcMenu = new ArrayList<>();
//        kfcMenu.add(new DishesModel("Mighty Zinger", "mighty_zinger", "KFC","kfc_logo", "Two Zinger pieces with cheese, lettuce, mayonnaise, spicy mayonnaise and round bread", 127.00));
//        kfcMenu.add(new DishesModel("Rizo", "kfc_rizo", "KFC", "kfc_logo","Rice topped with chicken with special spicy sauce", 39.00));
//        kfcMenu.add(new DishesModel("Monster Meal", "monster_meal", "KFC", "kfc_logo","Monster sandwich + fries + drink", 130.00));
//        kfcMenu.add(new DishesModel("Bucket Meal", "bucket_meal", "KFC ", "kfc_logo","12 pieces chicken + 1 large coleslaw + family fries + 4 buns + 1 Large Drink", 349.00));
//        adapterMenu = new MenuAdaptor(kfcMenu);
//
//        restaurants.add(new RestaurantsModel("KFC", "kfc_logo", kfcMenu));
//
//        ArrayList<DishesModel> mcdonaldsMenu = new ArrayList<>();
//        mcdonaldsMenu.add(new DishesModel("Big Mac", "big_mac", "McDonald's", "mcdonalds","Two beef patties, big mac sauce, melting signature cheese, crisp shredded lettuce, onions, pickles and a bun in the middle.", 55.00));
//        mcdonaldsMenu.add(new DishesModel("Big Tasty", "big_tasty", "McDonald's", "mcdonalds","Beef patty, emmental cheese and topped with sliced tomato, shredded lettuce, onions and big tasty sauce.", 91.00));
//        mcdonaldsMenu.add(new DishesModel("Chicken Macdo", "chicken_macdo", "McDonald's", "mcdonalds","Chicken patty, with crunchy lettuce and mayo, all wrapped in a fresh bun.", 28.00));
//        mcdonaldsMenu.add(new DishesModel("Grand Chicken Spicy", "grand_chicken_spicy", "McDonald's", "mcdonalds","Spiced grand chicken patty, deluce sauce, tomato slices, crispy lettuce, emmental cheese", 91.00));
//        adapterMenu = new MenuAdaptor(mcdonaldsMenu);
//
//        restaurants.add(new RestaurantsModel("McDonald's", "mcdonalds", mcdonaldsMenu));
//
//        ArrayList<DishesModel> cookDoorMenu = new ArrayList<>();
//        cookDoorMenu.add(new DishesModel("Super Crunchy", "super_crunchy", "Cook Door", "cook_door_logo","Spicy crispy golden chicken pieces, topped with Majorca sauce, pickles, turkey & melted mozzarella cheese.", 89.00));
//        cookDoorMenu.add(new DishesModel("Cordon Bleu", "cordon_bleu", "Cook Door", "cook_door_logo","Marinated breaded chicken breasts pieces topped with smoked beef and melted mozzarella, Majorca sauce, pickles & cheddar cheese slice.", 89.00));
//        cookDoorMenu.add(new DishesModel("Chicken Friday", "chicken_friday", "Cook Door", "cook_door_logo","Marinated chicken thigh, tossed with marinated mushroom, melted mozzarella cheese, Majorca sauce, pickles & caramelized onion.", 83.00));
//        cookDoorMenu.add(new DishesModel("Mushroom Burger", "mushroom_burger", "Cook Door", "cook_door_logo","Grilled beef burger patty, curry sauce, mushroom, caramelized onion, texas sauce.", 83.00));
//        adapterMenu = new MenuAdaptor(cookDoorMenu);
//
//        restaurants.add(new RestaurantsModel("Cook Door", "cook_door_logo", cookDoorMenu));
//
//        ArrayList<DishesModel> papaMenu = new ArrayList<>();
//        papaMenu.add(new DishesModel("Chicken Ranch", "ranch_pizza", "Papa John's", "papa_johns_logo","Grilled chicken, tomato, fresh mushroom with ranch sauce.", 127.00));
//        papaMenu.add(new DishesModel("Pepperoni", "pepperoni_pizza", "Papa John's", "papa_johns_logo","Loaded with pepperoni and extra mozzarella cheese.", 120.00));
//        papaMenu.add(new DishesModel("Super Papa's", "super_papa", "Papa John's", "papa_johns_logo","Pepperoni, italian sausage, smoked veal, fresh mushroom, onion, green pepper and black olives", 120.00));
//        papaMenu.add(new DishesModel("Margherita", "margherita", "Papa John's", "papa_johns_logo","Mozzarella cheese and pizza sauce", 89.00));
//        adapterMenu = new MenuAdaptor(papaMenu);
//        restaurants.add(new RestaurantsModel("Papa John's", "papa_johns_logo", papaMenu));
//
//        ArrayList<DishesModel> aboMazenMenu = new ArrayList<>();
//        aboMazenMenu.add(new DishesModel("Beef Shawerma", "beef_shawerma", "Abo Mazen ElSoury", "abomazen","Beef shawerma, tomato, pickles and garlic sauce.", 52.00));
//        aboMazenMenu.add(new DishesModel("Chicken Shawerma", "chicken_shawerma", "Abo Mazen ElSoury", "abomazen","Chicken shawerma, tomato, pickles and garlic sauce.", 120.00));
//        aboMazenMenu.add(new DishesModel("Emlak Shawerma Plate", "emlak_shawerma", "Abo Mazen ElSoury", "abomazen","Meat and chicken slices, basmati rice, kobeba, sambousek, vine leaves, cucumber, fries, hummus and garlic dip", 110.00));
//        aboMazenMenu.add(new DishesModel("Hummus Falafel", "hummus_falafel", "Abo Mazen ElSoury", "abomazen"," 6 pieces Falafel made with hummus served with garlic dip ", 18.00));
//        adapterMenu = new MenuAdaptor(aboMazenMenu);
//        restaurants.add(new RestaurantsModel("Abo Mazen ElSoury", "abomazen", aboMazenMenu));
//
//        ArrayList<DishesModel> pregoMenu = new ArrayList<>();
//        pregoMenu.add(new DishesModel("Magic Box", "magic_box", "Prego", "prego","1 Chicken cruncho + 1 Shish tawook sandwich, served with family fries, and 2 coca-cola", 167.00));
//        pregoMenu.add(new DishesModel("Shish Flames", "shish_flames", "Prego", "prego","Grilled Shish tawook, tehina salad or garlic dip and lebanese bread.", 77.00));
//        pregoMenu.add(new DishesModel("Winner Box", "winner_box", "Prego", "prego","2 chicken supreme sandwiches, 1/4 chicken meal with 2 shish tawook & 2 kofta, served with rice, bread, tehina or garlic dip.", 199.00));
//        pregoMenu.add(new DishesModel("Star Mix", "star_mix", "Prego", "prego","2 grilled breasts, 2 chicken tawook, 2 kofta, 2 oriental sojouk, rice and mexican chicken, bread, 1 tehina", 135.00));
//        adapterMenu = new MenuAdaptor(pregoMenu);
//        restaurants.add(new RestaurantsModel("Prego", "prego", pregoMenu));
//
//        ArrayList<DishesModel> cinnabonMenu = new ArrayList<>();
//        cinnabonMenu.add(new DishesModel("Caramel Pecanbon Roll", "caramel_pecanbon", "Cinnabon", "cinnabon_logo","Warm dough, legendary makara cinnamon, and topped with caramel frosting and pecans.", 99.00));
//        cinnabonMenu.add(new DishesModel("Chocobon", "chocobon", "Cinnabon", "cinnabon_logo","Warm dough, stuffed with chocolate and topped with rich cream cheese frosting & chocolate syrup", 79.00));
//        cinnabonMenu.add(new DishesModel("Classic Roll", "classic_cinnabon", "Cinnabon", "cinnabon_logo","Warm dough, legendary makara cinnamon, topped with rich cream cheese frosting", 69.00));
//        cinnabonMenu.add(new DishesModel("Classic Bites", "classic_bites", "Cinnabon", "cinnabon_logo","$ small bons of warm dough, legendary makara cinnamon, topped with rich cream cheese frosting", 48.00));
//        adapterMenu = new MenuAdaptor(cinnabonMenu);
//        restaurants.add(new RestaurantsModel("Cinnabon", "cinnabon_logo", cinnabonMenu));
//
//        ArrayList<DishesModel> buffaloMenu = new ArrayList<>();
//        buffaloMenu.add(new DishesModel("Bacon Mushroom Jack", "bacon_mushroom", "Buffalo Burger", "buffalo_logo","Beef bacon with fresh sauteed mushroom, cheddar cheese, creamy mayonnaise and pure beef burger patty.", 125.00));
//        buffaloMenu.add(new DishesModel("Charbroiled BBQ", "charbroiled_bbq", "Buffalo Burger", "buffalo_logo","Grilled burger topped with sweet onion, BBQ sauce, creamy Charbroiled sauce and swiss cheese.", 105.00));
//        buffaloMenu.add(new DishesModel("Old School", "old_school", "Buffalo Burger", "buffalo_logo","Pure beef burger patty, topped with our signature Buffalo sauce and cheddar cheese.", 100.00));
//        buffaloMenu.add(new DishesModel("Cholo's", "cholos", "Buffalo Burger", "buffalo_logo","Pickled sliced jalapenos, buffalo sauce and cheddar cheese on top of our pure beef burger patty.", 100.00));
//        adapterMenu = new MenuAdaptor(buffaloMenu);
//        restaurants.add(new RestaurantsModel("Buffalo Burger", "buffalo_logo", buffaloMenu));
//
//        ArrayList<DishesModel> munchAndShakeMenu = new ArrayList<>();
//        munchAndShakeMenu.add(new DishesModel("Shrooms Burger", "shrooms_burger", "Munch & Shake", "burger_king","Beef patty, mushroom, black pepper sauce, caramelized onion and emmental cheese.", 99.00));
//        munchAndShakeMenu.add(new DishesModel("Smokey Burger", "smokey_burger", "Munch & Shake", "burger_king","Beef patty, bacon, BBQ sauce, American cheese, lettuce, pickles", 96.00));
//        munchAndShakeMenu.add(new DishesModel("Spicy Wild Buffalo", "spicy_wild", "Munch & Shake", "burger_king","Chunky chicken breast, buffalo sauce, jalapeno,ranch sauce, cheese, lettuce and pickled cucumber.", 109.00));
//        munchAndShakeMenu.add(new DishesModel("Caramel Milkshake", "milkshake", "Munch & Shake", "burger_king","Milkshake with caramel sauce.", 57.00));
//        adapterMenu = new MenuAdaptor(munchAndShakeMenu);
//        restaurants.add(new RestaurantsModel("Munch & Shake", "burger_king", munchAndShakeMenu));
//
//        ArrayList<DishesModel> burgerKingMenu = new ArrayList<>();
//        burgerKingMenu.add(new DishesModel("Double Whopper", "double_whopper", "Burger King", "burger_king","Bun 5 inch, 2pcs whopper patties, tomatoes, lettuce, onion, pickles, ketchup and mayonnaise.", 95.00));
//        burgerKingMenu.add(new DishesModel("Chicken Royal", "chicken_royal", "Burger King", "burger_king","Bun 7 inch, chicken royal patty, lettuce and mayonnaise.", 65.00));
//        burgerKingMenu.add(new DishesModel("Steakhouse Beef", "steakhouse_beef", "Burger King", "burger_king","Bun 4.5 inch, whopper patty, american cheese, beef bacon, mayonnaise, lettuce, tomato, fried onion.", 91.00));
//        burgerKingMenu.add(new DishesModel("Cheese Burger", "cheese_burgerking", "Burger King", "burger_king","Bun 4 inch, burger patty, cheese, ketchup, mustard, pickles.", 24.00));
//        adapterMenu = new MenuAdaptor(burgerKingMenu);
//        restaurants.add(new RestaurantsModel("Burger King", "burger_king", burgerKingMenu));
//
//        adapterRestaurants = new RestaurantsAdaptor(restaurants);
//        recyclerRestaurantsList.setAdapter(adapterRestaurants);
//
//        restaurantsRef.setValue(restaurants);

    }

    private void recyclerPopular(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerPopularList = findViewById(R.id.recyclerPopular);
        recyclerPopularList.setLayoutManager(linearLayoutManager);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference popularRef = database.getReference("Popular");

        ArrayList<DishesModel> popularDishes = new ArrayList<>();

        popularRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                popularDishes.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    popularDishes.add(postSnapshot.getValue(DishesModel.class));
                }
                adapterPopular = new PopularAdaptor(popularDishes);
                recyclerPopularList.setAdapter(adapterPopular);
                recyclerPopularList.setLayoutManager(linearLayoutManager);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Failure", "Failed");
            }
        });

//        ArrayList<DishesModel> popularDishes = new ArrayList<>();
//        popularDishes.add(new DishesModel("Chicken Ranch", "ranch_pizza", "Papa John's", "papa_johns_logo", "Fresh , tomato with special sauce", 127.00));
//        popularDishes.add(new DishesModel("Mighty Zinger", "mighty_zinger", "Kentucky", "kfc_logo","Two Zinger pieces with cheese, lettuce, mayonnaise, spicy mayonnaise and round bread", 127.00));
//        popularDishes.add(new DishesModel("Super Crunchy", "super_crunchy", "Cook Door", "cook_door_logo","Spicy crispy golden chicken pieces, topped with Majorca sauce, pickles, turkey and melted mozzarella cheese.", 89.00));
//        popularDishes.add(new DishesModel("Big Mac", "big_mac", "McDonald's", "mcdonalds","Two beef patties, big mac sauce, melting signature cheese, crisp shredded lettuce, onions, pickles and a bun in the middle.", 55.00));
//
//        adapterPopular = new PopularAdaptor(popularDishes);
//        recyclerPopularList.setAdapter(adapterPopular) ;
//        popularRef.setValue(popularDishes);
    }
}