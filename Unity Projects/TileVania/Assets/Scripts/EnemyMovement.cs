using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyMovement : MonoBehaviour
{
    private Rigidbody2D enemyRigidBody;
    [SerializeField] private float enemySpeed;
    private Collider2D enemyFaceCollider;
    public bool isDefeated; 
    [SerializeField] private bool isFacingLeft;

    void Start()
    {
        enemyRigidBody = GetComponent<Rigidbody2D>();
        enemyFaceCollider = GetComponent<BoxCollider2D>();
        Move();
    }


    void Update()
    {
        
    }

    private void Move()
    {
        int directionMultiplier;
        if (isFacingLeft)
        {
            directionMultiplier = -1;
        }
        else
        {
            directionMultiplier = 1;
        }
        enemyRigidBody.velocity = new Vector2(enemySpeed*directionMultiplier, 0f);
    }

    

    private void FlipEnemy()
    {
        isFacingLeft = !isFacingLeft;
        transform.localScale = new Vector2(-1 * transform.localScale.x, transform.localScale.y);
        Move();
    }

    private void OnTriggerExit2D(Collider2D other)
    {
        if (other.gameObject.CompareTag("Hazard"))
        {
            return;
        }
        FlipEnemy();
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.gameObject.CompareTag("Hazard"))
        {
            FlipEnemy();    
        }
    }
}